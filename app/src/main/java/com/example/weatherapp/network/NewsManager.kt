package com.example.weatherapp.network

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.weatherapp.models.ArticleCategory
import com.example.weatherapp.models.Source
import com.example.weatherapp.models.TopNewsResponse
import com.example.weatherapp.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager(private val service: NewsService) {
    // this class takes care of merging everything together

    private val _newsResponse = mutableStateOf(TopNewsResponse())
    val newsResponse: State<TopNewsResponse>
    @Composable get() = remember {
        _newsResponse
    }

    val sourceName =  mutableStateOf("abc-news")

    private val _getArticlesBySources = mutableStateOf(TopNewsResponse())
    val getArticlesBySources: MutableState<TopNewsResponse>
        @Composable get() = remember { _getArticlesBySources }



    private val _searchedNewsResponse = mutableStateOf(TopNewsResponse())
    val searchedNewsResponse: MutableState<TopNewsResponse>
        @Composable get() = remember { _searchedNewsResponse }




    val query = mutableStateOf("")

    val selectedCategory : MutableState<ArticleCategory?> = mutableStateOf(null)


    suspend fun getArticles(country: String): TopNewsResponse = withContext(Dispatchers.IO){
        service.getTopArticles(country = country)
    }

    suspend fun getArticlesByCategory(category: String): TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticlesByCategory(category = category)
    }

    suspend fun getArticlesBySources(source: String) : TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticleBySources(source = source)
    }

    suspend fun getSearchedArticle(query: String): TopNewsResponse = withContext(Dispatchers.IO){
        service.getArticle(query)
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}



//State ensures that the news is available for other classes,
//when data changes in state, we don't update it manually, instead, it will update automatically.

//this is the ui now, we have taken care of the logic