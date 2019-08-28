package com.example.weatherapplication

import android.util.Log
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.example.weatherapplication.WeatherDataManager
import com.example.weatherapplication.common.Constants.API_KEY
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import org.json.JSONObject
import retrofit2.adapter.rxjava2.Result.response
import android.R.string
import com.beust.klaxon.JsonReader
import com.beust.klaxon.Klaxon
import com.google.gson.JsonParser
import java.io.StringReader


class WeatherPresenter(_view: MvpInterface.View): MvpInterface {

    private var view = _view
    private var parser = Parser()

    val getWeatherData by lazy {
        GetData.loadData()
    }

    fun requestDataFromServer() {
        val gson = Gson()
       getWeatherData.getWeatherData("London", API_KEY)
           .enqueue{
               onResponse = {

                   Log.d("Result", "${it.isSuccessful()}")
                   Log.d("Result", "${it.body()?.string()}")
                   Log.d("Result", "${it.headers()}")
                   Log.d("Result", "${it.message()}")
                   Log.d("Result", "${it.raw()}")
//                   Log.d("HERER", )



//                   val ss = Klaxon()
//                       .parseJsonObject(StringReader(it.body()!!.string()))

//                   println(ss)
                   val temp = JsonParser().parse(it.body()!!.string())
                   print(temp);
                   val json = JSONObject(it.body()?.string())
                   println(json);


//                   val json = it.body()?.string()
//                   val gson = GsonBuilder().setPrettyPrinting().create()
//
//                   println("=== Map from JSON ===")
//                   var personMap: Map<String, Any> = gson.fromJson(json, object : TypeToken<Map<String, Any>>() {}.type)
////                   personMap.forEach { println(it) }
//
//                   println("=== Map to JSON ===")
//                   val jsonPersonMap: String = gson.toJson(personMap)
//                   println(jsonPersonMap)
               }
               onFailure = {
                   Log.d("Error", "There are ${it} issue")

               }
           }

    }

   fun<T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
       val callBackKt = CallBackKt<T>()
       callback.invoke(callBackKt)
       this.enqueue(callBackKt)
   }

               class CallBackKt<T>: Callback<T> {

           var onResponse: ((Response<T>) -> Unit)? = null
           var onFailure: ((t: Throwable?) -> Unit)? = null

           override fun onFailure(call: Call<T>, t: Throwable) {
               onFailure?.invoke(t)
           }

           override fun onResponse(call: Call<T>, response: Response<T>) {
               onResponse?.invoke(response)
           }

       }

}


