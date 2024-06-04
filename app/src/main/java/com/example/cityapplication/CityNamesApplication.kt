package com.example.cityapplication

import android.app.Application
import com.example.cityapplication.data.AppContainer
import com.example.cityapplication.data.DefaultAppContainer

class CityNamesApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}