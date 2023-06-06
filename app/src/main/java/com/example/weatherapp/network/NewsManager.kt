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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsManager {
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


    private val _getArticlesByCategory = mutableStateOf(TopNewsResponse())
    val getArticlesByCategory: MutableState<TopNewsResponse>
        @Composable get() = remember { _getArticlesByCategory }

    private val _searchedNewsResponse = mutableStateOf(TopNewsResponse())
    val searchedNewsResponse: MutableState<TopNewsResponse>
        @Composable get() = remember { _searchedNewsResponse }




    val query = mutableStateOf("")

    val selectedCategory : MutableState<ArticleCategory?> = mutableStateOf(null)

    init {
        getArticles()
    }

    private fun getArticles(){
        val service = Api.retrofitService.getTopArticles("us")
        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful){
                    _newsResponse.value = response.body()!!
                    Log.d("news", "${_newsResponse.value}")
                }else{
                    Log.d("error", "${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getArticlesByCategory(category: String){
        val service = Api.retrofitService.getArticlesByCategory(category)
        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful){
                    _getArticlesByCategory.value = response.body()!!
                    Log.d("category", "${_getArticlesByCategory.value}")
                }else{
                    Log.d("error", "${response.code()}")
                }
            }

            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun getArticlesBySources(){
        val service = Api.retrofitService.getArticleBySources(sourceName.value)
        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful){
                    _getArticlesBySources.value = response.body()!!
                    Log.d("source", "${_getArticlesBySources.value}")
                }else{
                    Log.d("source", "${response.code()}")
                }
            }
            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("source", "${t.printStackTrace()}")
            }

        })
    }

    fun getSearchedArticle(query: String){
        val service = Api.retrofitService.getArticle(query)
        service.enqueue(object: Callback<TopNewsResponse> {
            override fun onResponse(
                call: Call<TopNewsResponse>,
                response: Response<TopNewsResponse>
            ) {
                if(response.isSuccessful){
                    _searchedNewsResponse.value = response.body()!!
                    Log.d("source", "${_searchedNewsResponse.value}")
                }else{
                    Log.d("error", "${response.code()}")
                }
            }
            override fun onFailure(call: Call<TopNewsResponse>, t: Throwable) {
                Log.d("error", "${t.printStackTrace()}")
            }

        })
    }

    fun onSelectedCategoryChanged(category: String){
        val newCategory = getArticleCategory(category = category)
        selectedCategory.value = newCategory
    }
}



//State ensures that the news is available for other classes,
//when data changes in state, we don't update it manually, instead, it will update automatically.

//this is the ui now, we have taken care of the logic