package com.example.weatherapplication


data class WeatherData(val cityName: String, val countryCode: String) {
    lateinit var id: Integer

    lateinit var state: String

    var temperature: String = ""

    lateinit var temp_high: String

    lateinit var temp_low: String

    lateinit var humidity: String

    var iconURL: String = "a"
}
