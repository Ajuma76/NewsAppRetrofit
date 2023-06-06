package com.example.weatherapp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.components.BottomMenu
import com.example.weatherapp.models.Source
import com.example.weatherapp.models.TopNewsArticle
import com.example.weatherapp.network.NewsManager
import com.example.weatherapp.screen.Categories
import com.example.weatherapp.screen.DetailScreen
import com.example.weatherapp.screen.Sources
import com.example.weatherapp.screen.TopNews

@Composable
fun NewsApp(){
    val scrollState = rememberScrollState()
    val navController = rememberNavController()
    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources
    )
    MainScreen(navController, scrollState)

}

@Composable
fun MainScreen(navController: NavHostController, scrollState: ScrollState){
    Scaffold(bottomBar = { BottomMenu(navController)}) {

        Navigation(navController = navController, scrollState = scrollState, paddingValues = it)
    }
}



@Composable
fun Navigation(navController: NavHostController,
               scrollState: ScrollState,
               newsManager: NewsManager = NewsManager(),
               paddingValues: PaddingValues){

    val articles = mutableListOf(TopNewsArticle())
    articles.addAll(newsManager.newsResponse.value.articles ?: listOf(TopNewsArticle()))


    Log.d("news", "$articles")
    articles.let{
        NavHost(navController = navController,
            startDestination = BottomMenuScreen.TopNews.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ){
            bottomNavigation(navController = navController, articles, newsManager)

            composable("DetailsScreen/{index}",
                arguments = listOf(navArgument("index"){type = NavType.IntType})
            ){
                    navBackStackEntry ->
                val index = navBackStackEntry.arguments?.getInt("index")
                index?.let {
                    if (newsManager.query.value != ""){
                        articles.clear()
                        articles.addAll(newsManager.searchedNewsResponse.value.articles ?: listOf())
                    }else{
                        articles.clear()
                        articles.addAll(newsManager.newsResponse.value.articles ?: listOf())
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
    newsManager: NewsManager
){
    composable(BottomMenuScreen.TopNews.route){
        TopNews(navController = navController, article, newsManager.query, newsManager = newsManager)
    }

    composable(BottomMenuScreen.Categories.route) {
        newsManager.getArticlesByCategory("business")
        newsManager.onSelectedCategoryChanged("business")

        Categories(newsManager = newsManager, onFetchCategory = {
            newsManager.onSelectedCategoryChanged(it)
            newsManager.getArticlesByCategory(it)
        })
    }

    composable(BottomMenuScreen.Sources.route) {
        Sources(newsManager = newsManager)
    }
}
