package com.example.weatherapp

import android.app.Application
import com.example.weatherapp.data.Repository
import com.example.weatherapp.network.Api
import com.example.weatherapp.network.NewsManager

class MainApp: Application() {
    //were going to initialize the newsmanager as the repository needs it
// when we initialize the repository providing the newsmanager as the argument

    private val manager by lazy {
        NewsManager(Api.retrofitService)
    }

    val repository by lazy {
        Repository(manager = manager)
    }
}