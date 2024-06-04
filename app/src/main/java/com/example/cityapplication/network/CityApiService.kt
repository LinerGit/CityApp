package com.example.cityapplication.network

import com.example.cityapplication.model.City
import retrofit2.http.GET

interface CityApiService {
    @GET("cities.json")
    suspend fun getNames(): List<City>
}

