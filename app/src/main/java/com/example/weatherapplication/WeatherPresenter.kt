package com.example.weatherapplication

import com.example.weatherapplication.common.Constants
import com.example.weatherapplication.common.Constants.API_KEY
import com.example.weatherapplication.common.Constants.KELVIN_TO_F
import com.example.weatherapplication.common.Constants.TEMPERATURE_CONST
import org.json.JSONObject


class WeatherPresenter {


    val getWeatherData by lazy {
        GetData.loadData()
    }

    fun requestDataFromServer(
        weatherDataInput: MutableList<WeatherData>,
        weatherDataOutput: MutableList<WeatherData>
    ) {

        for (cityInfo in weatherDataInput) {
            val it = getWeatherData.getWeatherData(cityInfo.cityName, API_KEY)
                .execute()

            val json = JSONObject(it.body()?.string())
            val main = JSONObject(json.get("main")?.toString())

            // Get the icon url
            val weatherArray = json.getJSONArray("weather")
            val weatherIcon = JSONObject(weatherArray.get(0).toString())
            val iconSb = StringBuilder(Constants.BASE_URL)
            iconSb.append(Constants.ICON_PATH)
            iconSb.append(weatherIcon.get("icon"))
            iconSb.append(Constants.ICON_EXTENSION)
            cityInfo.iconURL = iconSb.toString()

            // Get the rest of the weather data
            val temperature = kelvinToF(main.get("temp"))
            val temperature_high = kelvinToF(main.get("temp_max"))
            val temperature_low = kelvinToF(main.get("temp_min"))
            val humidity = main.get("humidity").toString()
            val id = json.get("id")
            cityInfo.temp_high = temperature_high
            cityInfo.temp_low = temperature_low
            cityInfo.temperature = temperature
            cityInfo.humidity = humidity
        }
    }

    fun kelvinToF(kelvin: Any): String {
        val kelvinToDouble = kelvin as Double
        val toF = "%.1f".format(kelvinToDouble.minus(KELVIN_TO_F) * 9 / 5 + TEMPERATURE_CONST)
        return "${toF}${0x00B0.toChar()}F"
    }

}



