package com.solutelabs.foodrecipeapp.model

data class RecipeSearchResponse(

    var count: Int,
    var results: List<Recipe>

)
