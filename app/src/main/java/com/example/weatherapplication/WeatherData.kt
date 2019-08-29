package com.example.weatherapplication

import android.os.Parcel
import android.os.Parcelable
import com.example.weatherapplication.common.Constants.KELVIN_TO_F
import kotlinx.android.parcel.Parcelize

//@Serializable
//@Parcelize
data class WeatherData(val cityName: String, val countryCode: String)
//    var _cityName: String,
//    var _countryCode: String
 {
    lateinit var id: Integer

    lateinit var state: String

    var temperature: String = ""

//
//    var cityName =""
//        get()=_cityName
//        set (value){
//            field = value
//        }
//
//    var countryCode =""
//        get()=_countryCode
//        set(value){
//            field = value
//        }

    lateinit var temp_high: String

    lateinit var temp_low: String

    lateinit var humidity: String

    lateinit var iconURL: String
}
