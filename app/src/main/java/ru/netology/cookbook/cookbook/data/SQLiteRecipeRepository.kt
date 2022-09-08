package ru.netology.cookbook.cookbook.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import androidx.room.Transaction
import ru.netology.cookbook.cookbook.classes.Recipe
import ru.netology.cookbook.cookbook.dataBase.CategoryIdEntity
import ru.netology.cookbook.cookbook.dataBase.RecipeDao

class SQLiteRecipeRepository(
    private val dao: RecipeDao
) : RecipeRepository {

    private var categoriesLiveData: MutableLiveData<Set<Int>> = MutableLiveData()

    override var data: LiveData<List<Recipe>> =
        Transformations.switchMap(categoriesLiveData) { list ->
            dao.getCategories(list).map { entities ->
                entities.map { it.toModel() }
            }
        }

    override fun setFilter(categories: Set<Int>) {
        categoriesLiveData.value = categories
    }

    override fun liked(recipeId: Long) {
        dao.likeById(recipeId)
    }

    override fun delete(recipeId: Long) {
        dao.removeById(recipeId)
    }

    @Transaction
    override fun update(recipe: Recipe) {
        dao.removeById(recipe.id)
        val id = dao.insertRecipe(recipe.toEntity(0).recipeEntity)
        recipe.toEntity(id).stagesEntity.map {
            dao.insertStage(it.copy(stageId = 0))
        }
        recipe.categories.map {
            dao.insertCategory(CategoryIdEntity(0, id, it))
        }
    }

    @Transaction
    override fun insert(recipe: Recipe) {
        val id = dao.insertRecipe(recipe.toEntity(0).recipeEntity)
        recipe.toEntity(id).stagesEntity.map {
            dao.insertStage(it.copy(stageId = 0))
        }
        recipe.categories.map {
            dao.insertCategory(CategoryIdEntity(0, id, it))
        }
    }
}