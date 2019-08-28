package com.example.weatherapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WeatherAdapter(private val cityNameSet: MutableList<String>,
                     private val temperatureSet: MutableList<String>) :
    RecyclerView.Adapter<WeatherAdapter.CityViewHolder>() {
    class CityViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.view_holder_city, parent, false)
    ){
        private val cityTitle = itemView.findViewById<TextView>(R.id.city_text_view)
        private val temperature = itemView.findViewById<TextView>(R.id.temperature_text_view)
        private val temperatureIcon = itemView.findViewById<ImageView>(R.id.temperature_icon)

        fun bind(title: String, temperatureValue: String) {
            cityTitle.text = title
            temperature.text = temperatureValue
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): WeatherAdapter.CityViewHolder {
        return CityViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cityNameSet[position], temperatureSet[position])
    }

    override fun getItemCount() = cityNameSet.size
}