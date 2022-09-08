package ru.netology.cookbook.cookbook.data

import androidx.lifecycle.LiveData
import ru.netology.cookbook.cookbook.classes.Recipe

interface RecipeRepository {

    val data: LiveData<List<Recipe>>

    fun insert(recipe: Recipe)
    fun update(recipe: Recipe)
    fun delete(recipeId:Long)
    fun liked(recipeId:Long)
    fun setFilter(categories: Set<Int>)
}