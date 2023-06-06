package com.example.weatherapp.models
import com.example.weatherapp.models.ArticleCategory.*

enum class ArticleCategory (val categoryName: String){
    //enum class allows us to create keywords that we assign certain values to.

    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    HEALTH("health"),
    SPORT("sport"),
    SCIENCE("science"),
    TECHNOLOGY("technology")
}

fun getAllArticleCategory():List<ArticleCategory>{
    return listOf(
        ArticleCategory.BUSINESS,
        ArticleCategory.ENTERTAINMENT,
        ArticleCategory.HEALTH,
        ArticleCategory.SPORT,
        ArticleCategory.SCIENCE,
        ArticleCategory.TECHNOLOGY
    )
}

fun getArticleCategory(category: String): ArticleCategory?{
    val map = values().associateBy(ArticleCategory::categoryName)
    return map[category]
}