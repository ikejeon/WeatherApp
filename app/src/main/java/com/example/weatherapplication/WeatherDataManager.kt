package com.example.weatherapplication

import retrofit2.Retrofit

import com.example.weatherapplication.common.Constants.BASE_URL
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object WeatherDataManager{

    fun loadData():GetData? {
      return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build().create(GetData::class.java)

    }


}