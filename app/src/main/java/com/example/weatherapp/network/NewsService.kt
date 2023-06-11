package com.example.weatherapp.network

import com.example.weatherapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    suspend fun getTopArticles(
        @Query("country")  country: String,
    ): TopNewsResponse


    @GET("top-headlines")
    suspend fun getArticlesByCategory(
        @Query("category") category: String,
    ): TopNewsResponse


    @GET("everything")
    suspend fun getArticleBySources(
        @Query("source") source: String,
    ): TopNewsResponse

    @GET("everything")
    suspend fun getArticle(
        @Query("q") query: String,
    ): TopNewsResponse

}
//NewsService is an interface class that will help us send out requests to the API/