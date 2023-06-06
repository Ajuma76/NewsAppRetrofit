package com.example.weatherapp.components

import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherapp.BottomMenuScreen
import com.example.weatherapp.R



@Composable
fun BottomMenu(navController: NavController) {
    val menuItems = listOf(
        BottomMenuScreen.TopNews,
        BottomMenuScreen.Categories,
        BottomMenuScreen.Sources
    )

    BottomNavigation(backgroundColor = Color.Gray,
        contentColor = Color.Black) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination
        menuItems.forEach {screen->
            currentRoute?.hierarchy?.any { it.route==screen.route }?.let {
                BottomNavigationItem(
                    label = { Text(text = screen.title)},
                    alwaysShowLabel = true,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.White,
                    selected = it,
                    onClick = {
                        navController.navigate(screen.route)
                    },
                    icon = { Icon(imageVector = screen.icon, contentDescription = screen.title)})
            }
        }
    }
//    BottomNavigation(backgroundColor = Color.LightGray
//        ,contentColor = colorResource(id = R.color.white)) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        menuItems.forEach {
//            BottomNavigationItem(
//                label = { Text(text = it.title) },
//                alwaysShowLabel = true,
//                selectedContentColor = Color.White,
//                unselectedContentColor = Color.Black,
//                selected = currentRoute == it.route,
//                onClick = {
//                          navController.navigate(it.route){
//                              navController.graph.startDestinationRoute?.let{
//                                  route ->
//                                  popUpTo(route){
//                                      saveState = true
//                                  }
//                              }
//                              launchSingleTop = true
//                              restoreState = true
//                          }
//                },
//                icon = { Icon(imageVector = it.icon, contentDescription = it.title) }
//            )
//        }
//    }
}