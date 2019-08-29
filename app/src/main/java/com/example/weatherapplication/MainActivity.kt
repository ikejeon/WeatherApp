package com.example.weatherapplication

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : Activity(), MvpInterface.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val weatherDataList = mutableListOf<WeatherData>()
    private val updatedWeatherDataList = mutableListOf<WeatherData>()

    private var presenter: WeatherPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = WeatherPresenter()

        val cityWeather1 = WeatherData("San Francisco", "US")
        val cityWeather2 = WeatherData("New York", "US")
        val cityWeather3 = WeatherData("Salt Lake City", "UT")

        weatherDataList.add(cityWeather1)
        weatherDataList.add(cityWeather2)
        weatherDataList.add(cityWeather3)

        Thread({
            //Do some Network Request
            presenter!!.requestDataFromServer(weatherDataList, updatedWeatherDataList)
            runOnUiThread({
                //Update UI
                recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = WeatherAdapter(weatherDataList, this@MainActivity)
                    Toast.makeText(this@MainActivity, "${weatherDataList[0].humidity}", Toast.LENGTH_LONG).show()
                }
            })
        }).start()
    }
}
