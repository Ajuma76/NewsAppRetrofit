package com.example.weatherapp.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.MainApp
import com.example.weatherapp.models.ArticleCategory
import com.example.weatherapp.models.TopNewsResponse
import com.example.weatherapp.models.getArticleCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = getApplication<MainApp>().repository
    //newsresponse
    private val _newResponse = MutableStateFlow(TopNewsResponse())
    val newsResponse: StateFlow<TopNewsResponse>
    get() = _newResponse

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun getTopArticles(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _newResponse.value = repository.getArticles()
        }
        _isLoading.value = false
    }

    //category
    private val _getArticlesByCategory = MutableStateFlow(TopNewsResponse())
    val getArticlesByCategory: StateFlow<TopNewsResponse>
    get() = _getArticlesByCategory

    private val _selectedCategory : MutableStateFlow<ArticleCategory?>
    = MutableStateFlow(null)
    val selectedCategory: StateFlow<ArticleCategory?>
    get() = _selectedCategory


    fun getArticlesByCategory(category: String){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _getArticlesByCategory.value = repository.getArticlesByCategory(category)
        }
        _isLoading.value = false
    }

    //source
    val sourceName = MutableStateFlow("cnn")
    private val _getArticleBySource = MutableStateFlow(TopNewsResponse())
    val getArticlesBySource : StateFlow<TopNewsResponse>
    get() = _getArticleBySource

    fun getArticleBySource(){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _getArticleBySource.value = repository.getArticlesBySource(sourceName.value)
        }
        _isLoading.value = false
    }

    //search
    val query = MutableStateFlow("")
    private val _searchedNewsResponse = MutableStateFlow(TopNewsResponse())
    val searchedNewsResponse : StateFlow<TopNewsResponse>
    get() = _searchedNewsResponse

    fun getSearchedArticle(query: String){
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.IO){
            _searchedNewsResponse.value = repository.getSearchedArticle(query)
        }
        _isLoading.value = false
    }


    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category)
        _selectedCategory.value =newCategory
    }
}