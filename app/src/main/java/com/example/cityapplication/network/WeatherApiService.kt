package com.example.cityapplication.network

import com.example.cityapplication.model.Weather
import retrofit2.http.GET

interface WeatherApiService {
    @GET("temp")
    suspend fun getTemp():List<Weather>
}