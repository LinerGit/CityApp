package com.example.cityapplication.network

import retrofit2.http.GET

interface WeatherApiService {
    @GET("temp")
    suspend fun getTemp():Float
}