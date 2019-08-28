package com.example.weatherapplication

import com.beust.klaxon.Json

data class WeatherData (
    val temperature: String,

    @Json(name = "name")
    val cityName: String
)
