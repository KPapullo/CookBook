package ru.netology.cookbook.cookbook.adapter

import ru.netology.cookbook.cookbook.classes.Recipe

interface RecipeInteractionListener {

    fun onRemoveClicked(recipeId: Long)
    fun onEditClicked(recipe: Recipe)
    fun onToRecipe(recipe: Recipe)
    fun onLikeClicked(recipeId: Long)
}