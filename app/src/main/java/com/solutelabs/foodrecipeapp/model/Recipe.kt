package com.solutelabs.foodrecipeapp.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Recipe(

    @SerializedName("pk")
    val pk: Int,

    val title: String,
    val publisher: String,
    val featured_image: String,
    val rating: Int,
    @SerializedName("source_url")
    val sourceUrl: String,
    val ingredients: List<String>,
    @SerializedName("date_added")
    val dateAdded: String,
    @SerializedName("date_updated")
    val dateUpdated: String,
    var isSaved: Boolean = false
)
