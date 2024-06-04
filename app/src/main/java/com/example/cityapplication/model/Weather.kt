package com.example.cityapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather (
    val temp:Float,
    val lon:Float,
    val lat:Float
)