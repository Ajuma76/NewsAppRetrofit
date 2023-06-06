package com.example.weatherapp.network

import com.example.weatherapp.models.TopNewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsService {

    @GET("top-headlines")
    fun getTopArticles(
        @Query("country")  country: String,
    ): Call<TopNewsResponse>


    @GET("top-headlines")
    fun getArticlesByCategory(
        @Query("category") category: String,
    ): Call<TopNewsResponse>


    @GET("everything")
    fun getArticleBySources(
        @Query("source") source: String,
    ): Call<TopNewsResponse>

    @GET("everything")
    fun getArticle(
        @Query("q") query: String,
    ): Call<TopNewsResponse>

}
//NewsService is an interface class that will help us send out requests to the API/