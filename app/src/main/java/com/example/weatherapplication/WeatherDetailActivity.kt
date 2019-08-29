package com.example.weatherapplication

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class WeatherDetailActivity: AppCompatActivity() {
    private var nameView: TextView? = null
    private var temperatureView: TextView? = null
    private var temperatureHighLowView: TextView? = null
    private var precipView: TextView? = null
    private var iconView: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)

        val gson = Gson()
        val strObj = intent.getStringExtra("city")
        val weatherData = gson.fromJson(strObj, WeatherData::class.java)

        nameView = findViewById(R.id.name)
        temperatureView = findViewById(R.id.temp)
        temperatureHighLowView = findViewById(R.id.low_high)
        precipView = findViewById(R.id.precipitation)
        iconView = findViewById(R.id.icon)

        nameView?.text = weatherData.cityName
        temperatureView?.text = weatherData.temperature
        temperatureHighLowView?.text = "${weatherData.temp_high + " / "+weatherData.temp_low}"
        precipView?.text = weatherData.humidity

        val iconUrl = weatherData.iconURL
        if (iconUrl != "") {
            Picasso.with(this).load(iconUrl).into(iconView)
        }
    }
}