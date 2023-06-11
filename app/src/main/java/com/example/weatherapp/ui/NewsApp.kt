package com.example.weatherapp.ui

import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.BottomMenuScreen
import com.example.weatherapp.components.BottomMenu
import com.example.weatherapp.models.TopNewsArticle
import com.example.weatherapp.network.Api
import com.example.weatherapp.network.NewsManager
import com.example.weatherapp.ui.screen.Categories
import com.example.weatherapp.ui.screen.DetailScreen
import com.example.weatherapp.ui.screen.Sources
import com.example.weatherapp.ui.screen.TopNews

@Composable
fun NewsApp(mainViewModel: MainViewModel){
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources
    )
    MainScreen(navController, scrollState, mainViewModel = mainViewModel)

}

@Composable
fun MainScreen(navController: NavHostController,
               scrollState: ScrollState,
               mainViewModel: MainViewModel){
    Scaffold(bottomBar = { BottomMenu(navController)}) {

        Navigation(navController = navController,
            scrollState = scrollState,
            paddingValues = it, viewModel = mainViewModel)
    }
}



@Composable
fun Navigation(navController: NavHostController,
               scrollState: ScrollState,
               newsManager: NewsManager = NewsManager(Api.retrofitService),
               paddingValues: PaddingValues,
               viewModel: MainViewModel){

    val articles = mutableListOf(TopNewsArticle())
    val topArticles = viewModel.newsResponse.collectAsState().value.articles
    articles.addAll(topArticles ?: listOf())


    Log.d("news", "$articles")
    articles.let{
        NavHost(navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ){
            val queryState = mutableStateOf(viewModel.query.value)
            bottomNavigation(navController = navController, articles, query = queryState, viewModel)

            composable("DetailsScreen/{index}",
                arguments = listOf(navArgument("index"){type = NavType.IntType})
            ){
                    navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    if (queryState.value != ""){
                        articles.clear()
                        articles.addAll(viewModel.searchedNewsResponse.value.articles ?: listOf())
                    }else{
                        articles.clear()
                        articles.addAll(viewModel.newsResponse.value.articles ?: listOf())
                    }
                    val article = articles[index]
                    DetailScreen(article, scrollState, navController)
                }
            }
        }
    }

}


fun NavGraphBuilder.bottomNavigation(
    navController: NavHostController,
    article: List<TopNewsArticle>,
    query: MutableState<String>,
    viewModel: MainViewModel
){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, article, query, viewModel = viewModel)
    }

    composable(BottomMenuScreen.Categories.route) {
        viewModel.getArticlesByCategory("business")
        viewModel.onSelectedCategoryChanged("business")

        Categories(viewModel = viewModel, onFetchCategory = {
            viewModel.onSelectedCategoryChanged(it)
            viewModel.getArticlesByCategory(it)
        })
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(viewModel = viewModel)
    }
}
