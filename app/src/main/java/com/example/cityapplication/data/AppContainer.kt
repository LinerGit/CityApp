package com.example.cityapplication.data

import com.example.cityapplication.network.CityApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val cityNamesRepository: CityNamesRepository
}

class DefaultAppContainer: AppContainer {
    private val baseUrl = "https://gist.githubusercontent.com/Stronger197/764f9886a1e8392ddcae2521437d5a3b/raw/65164ea1af958c75c81a7f0221bead610590448e/"
    private val weatherBaseUrl = ""
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: CityApiService by lazy {
        retrofit.create(CityApiService::class.java)
    }
    override val cityNamesRepository: CityNamesRepository by lazy {
        NetworkCityNamesRepository(retrofitService)
    }
}

