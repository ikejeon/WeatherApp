package com.example.weatherapplication

import android.util.Log
import com.example.weatherapplication.common.Constants.API_KEY

import org.json.JSONObject
import com.example.weatherapplication.common.Constants.KELVIN_TO_F
import com.example.weatherapplication.common.Constants.TEMPERATURE_CONST
import com.google.gson.JsonArray
import org.json.JSONArray
import java.util.*


class WeatherPresenter(_view: MvpInterface.View): MvpInterface {

    private var view = _view

    val getWeatherData by lazy {
        GetData.loadData()
    }

    fun requestDataFromServer(weatherDataInput:  MutableList<WeatherData>, weatherDataOutput: MutableList<WeatherData>) /*: MutableList<WeatherData>*/{
        val iterate = weatherDataInput.listIterator()
//        while (iterate.hasNext()) {
//        for (i in 0..weatherDataInput.size-1) {
        for (cityInfo in weatherDataInput) {
//        weatherDataInput.iterator().forEach {cityInfo->
//            val cityInfo = iterate.next()
            val it  = getWeatherData.getWeatherData(cityInfo.cityName, API_KEY)
                .execute()
            Log.d("Result", "${it.isSuccessful()}")

            val json = JSONObject(it.body()?.string())
            val main  =JSONObject(json.get("main")?.toString())
            val weatherArray = json.getJSONArray("weather")
            val weatherIcon = JSONObject(weatherArray.get(0).toString());

            val iconSb = StringBuilder("http://openweathermap.org/img/w/")
            iconSb.append(weatherIcon.get("icon"))
            iconSb.append(".png")
            cityInfo.iconURL = iconSb.toString()

                   val temperature = kelvinToF(main.get("temp"))
                   val temperature_high = kelvinToF(main.get("temp_max"))
                   val temperature_low = kelvinToF(main.get("temp_min"))
                   val humidity = main.get("humidity").toString()
//                   val icon = weatherDetail.get("icon")
                   val id = json.get("id")
//                   iterate
                    cityInfo.temp_high = temperature_high
                    cityInfo.temp_low = temperature_low
                    cityInfo.temperature = temperature
                    cityInfo.humidity = humidity
//                   println(icon)
//                   println(id)
////                   weatherDataOutput.add(cityInfo)
//
//
//               }
//               onFailure = {
//                   Log.d("Error", "There are ${it} issue")
//
//               }
//           }
                }
        }

//    }
//    fun onResponse(call: Call<List<Change>>, response: Response<List<Change>>) {
//        if (response.isSuccessful) {
//            val changesList = response.body()
//            changesList!!.forEach { change -> System.out.println(change.subject) }
//        } else {
//            println(response.errorBody())
//        }
//    }
//
//    fun onFailure(call: Call<List<Change>>, t: Throwable) {
//        t.printStackTrace()
//    }

//   fun<T> Call<T>.execute(callback: CallBackKt<T>.() -> Unit) {
//       val callBackKt = CallBackKt<T>()
//       callback.invoke(callBackKt)
//       this.execute()
//   }

//    class CallBackKt<T>: Callback<T> {
//
//       var onResponse: ((Response<T>) -> Unit)? = null
//       var onFailure: ((t: Throwable?) -> Unit)? = null
//
//       override fun onFailure(call: Call<T>, t: Throwable) {
//           onFailure?.invoke(t)
//       }
//
//       override fun onResponse(call: Call<T>, response: Response<T>) {
//           onResponse?.invoke(response)
//       }
//
//   }
    fun kelvinToF(kelvin: Any): String{
        val kelvinToDouble = kelvin as Double
        val toF = "%.1f".format(kelvinToDouble.minus(KELVIN_TO_F)*9/5 +TEMPERATURE_CONST)
        return "${toF}${0x00B0.toChar()}F"
    }

}



