package com.example.weatherapp.data

import com.example.weatherapp.network.NewsManager

class Repository (val manager: NewsManager) {
    suspend fun getArticles() = manager.getArticles("us")
    suspend fun getArticlesByCategory(category: String) = manager.getArticlesByCategory(category)
    suspend fun getArticlesBySource(source: String) = manager.getArticlesBySources(source = source)
    suspend fun getSearchedArticle(query: String) = manager.getSearchedArticle(query = query)

}