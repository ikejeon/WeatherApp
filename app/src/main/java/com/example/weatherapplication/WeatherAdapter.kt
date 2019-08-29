package com.example.weatherapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class WeatherAdapter(private val weatherDataList: MutableList<WeatherData>,
                     private val context: Context) :
    RecyclerView.Adapter<WeatherAdapter.CityViewHolder>() {
    class CityViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.view_holder_city, parent, false)
    ){
        private val cityTitle = itemView.findViewById<TextView>(R.id.city_text_view)
        private val temperature = itemView.findViewById<TextView>(R.id.temperature_text_view)
        private val icon = itemView.findViewById<ImageView>(R.id.temperature_icon)

        fun bind(
            title: String,
            temperatureValue: String,
            iconURL: String,
            context: Context
        ) {
            cityTitle.text = title
            temperature.text = temperatureValue
                Picasso.with(context).load(iconURL).into(icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): WeatherAdapter.CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val gson = Gson()
        holder.bind(weatherDataList[position].cityName, weatherDataList[position].temperature, weatherDataList[position].iconURL, context)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WeatherDetailActivity::class.java)
            intent.putExtra("city", gson.toJson(weatherDataList[position]))
            context.startActivity(intent)

        }

    }

    override fun getItemCount() = weatherDataList.size
}