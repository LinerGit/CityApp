package com.example.cityapplication.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class City(
    @SerialName("id")
    val id: String,
    @SerialName("city")
    val name: String,
    //val cityChar:String = name[0].toString(),
    @SerialName("latitude")
    val lat: String,
    @SerialName("longitude")
    val long: String
)
