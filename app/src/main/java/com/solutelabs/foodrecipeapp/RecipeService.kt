package com.solutelabs.foodrecipeapp


import com.solutelabs.foodrecipeapp.model.RecipeSearchResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


//https://food2fork.ca/api/recipe/search/?page=2&query=beef carrot potato onion

const val BASE_URL = "https://food2fork.ca/api/recipe/"
const val KEY_TOKEN = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48"

interface RecipeInterface {

    @Headers("Accept: application/json", "Authorization: ${KEY_TOKEN}")
    @GET("search/")
    fun getAllRecipes(
        @Query("page") page: Int,
        @Query("query") query: String
    ): Call<RecipeSearchResponse>

}


object RecipeService{
    val RecipeInstance : RecipeInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        RecipeInstance = retrofit.create(RecipeInterface::class.java)
    }
}



