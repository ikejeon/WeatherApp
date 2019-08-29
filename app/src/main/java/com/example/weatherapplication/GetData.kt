package com.example.weatherapplication

import com.example.weatherapplication.common.Constants.BASE_URL
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GetData {

    @GET("data/2.5/weather?")
    fun getWeatherData(
        @Query("q") query: String,
        @Query("appid") appid: String

    ): Call<ResponseBody>

    companion object WeatherDataManager {
        fun loadData(): GetData {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(GetData::class.java)
        }
    }

}