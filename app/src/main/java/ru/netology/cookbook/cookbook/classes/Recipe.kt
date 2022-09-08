package ru.netology.cookbook.cookbook.classes

import kotlinx.serialization.Serializable
import ru.netology.cookbook.cookbook.dataBase.CategoryIdEntity
import ru.netology.cookbook.cookbook.dataBase.RecipeEntity
import ru.netology.cookbook.cookbook.dataBase.RecipeWithStages
import ru.netology.cookbook.cookbook.dataBase.StageEntity

@Serializable
data class Recipe(
    val id: Long,
    val title: String,
    val author: String,
    val liked: Boolean = false,
    val stages: List<Stage>,
    val categories: List<Int>
) {

    internal fun toEntity(recipeId: Long) = RecipeWithStages(
        RecipeEntity(
            id = id,
            title = title,
            author = author,
            liked = liked
        ), categories.map { category ->
            CategoryIdEntity(
                keyId = 0,
                recipeId = recipeId,
                categoryId = category
            )
        },
        stages.map { stage ->
            StageEntity(
                stageId = stage.id,
                stage_recipe_Id = recipeId,
                content = stage.content,
                uriPhoto = stage.uriPhoto
            )
        }
    )
}