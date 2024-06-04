package com.example.cityapplication.data

import com.example.cityapplication.model.City
import com.example.cityapplication.network.CityApiService

interface CityNamesRepository {
    suspend fun getCityNames(): List<City>
}

class NetworkCityNamesRepository(
    private val cityApiService: CityApiService
) : CityNamesRepository {
    override suspend fun getCityNames(): List<City> = cityApiService.getNames()
}