package com.example.weatherapp.models

data class TopNewsResponse(
    val status: String? = null,
    val totalResult: Int? = null,
    val articles: List<TopNewsArticle>? = null
)
