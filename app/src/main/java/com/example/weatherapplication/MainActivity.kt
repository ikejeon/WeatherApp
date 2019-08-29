package com.example.weatherapplication

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapplication.common.Constants
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : Activity() {
    private lateinit var recyclerView: RecyclerView
    private val weatherDataList = mutableListOf<WeatherData>()
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
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
        viewAdapter = WeatherAdapter(weatherDataList, this@MainActivity)
        add_button.setOnClickListener {
                val taskEditText = EditText(this)
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Add city")
                    .setMessage("Enter the city name:")
                    .setView(taskEditText)
                    .setPositiveButton("Add", DialogInterface.OnClickListener { dialog, which ->
                        val name = taskEditText.text.toString()
                        val newCity = WeatherData(name, "")
                        weatherDataList.add(newCity)
                        viewAdapter.notifyItemChanged(weatherDataList.size - 1)
                        val refreshTask = RefreshTask()
                        refreshTask.execute(newCity)

                        //This adds to the list and even makes the call
//                        Thread({
//                            presenter!!.requestDataFromServer(WeatherData(name,"US"))
//                        }).start()

                    })
                    .setNegativeButton("Cancel", null)
                    .create()
                dialog.show()
            }


//        Thread({
//            //Do some Network Request
//            for (cityInfo in weatherDataList) {
//
//                presenter!!.requestDataFromServer(cityInfo)
//            }
//            runOnUiThread({
//                //Update UI
//                recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
//                    setHasFixedSize(true)
//                    layoutManager = LinearLayoutManager(this@MainActivity)
//                    adapter = viewAdapter
//                    Toast.makeText(this@MainActivity, "${weatherDataList[0].humidity}", Toast.LENGTH_LONG).show()
//                }
//            })
//        }).start()

        val refreshTask = RefreshTask()
        val params = arrayOf<WeatherData>(cityWeather1, cityWeather2, cityWeather3)
        refreshTask.execute(*params)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = viewAdapter
        }
    }

    inner class RefreshTask : AsyncTask<WeatherData, Void, Array<WeatherData>>() {

        override fun doInBackground(vararg p0: WeatherData): Array<WeatherData> {
            for (weatherData in p0) {
                val sb = StringBuilder(Constants.BASE_URL)
                sb.append(weatherData.cityName)
                sb.append("&APPID=")
                sb.append(Constants.API_KEY)

                try {
                    val url = URL(sb.toString())
                    val httpUrlConnection = url.openConnection() as HttpURLConnection
                    httpUrlConnection.requestMethod = "GET"
                    httpUrlConnection.connect()

                    if (httpUrlConnection.responseCode == HttpURLConnection.HTTP_OK) {

                        val responseBody = httpUrlConnection.inputStream
                        val responseJSON = readResponse(responseBody)
                        val jsonObj = JSONObject(responseJSON)

                        weatherData.temperature = kelvinToF(jsonObj.getJSONObject("main").getString("temp"))
                        weatherData.humidity = (jsonObj.getJSONObject("main").getString("humidity"))
                        weatherData.temp_high = kelvinToF(jsonObj.getJSONObject("main").getString("temp_max"))
                        weatherData.temp_low = kelvinToF(jsonObj.getJSONObject("main").getString("temp_min"))

                        val icon = jsonObj.getJSONArray("weather").getJSONObject(0).getString("icon") + ".png"
                        val iconSb = StringBuilder("http://openweathermap.org/img/w/")
                        iconSb.append(icon)
                        weatherData.iconURL = (iconSb.toString())
                    }
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
            }

            return p0 as Array<WeatherData>
        }

        override fun onPostExecute(updateData: Array<WeatherData>) {
            for (i in updateData.indices) {

                val position = weatherDataList.indexOf(updateData[i])
                weatherDataList.set(position, updateData[i])
                viewAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun readResponse(`is`: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(`is`))
        return bufferedReader.readLine()
    }

    fun kelvinToF(kelvin: String): String {
        val kelvinToDouble = kelvin.toDouble()
        val toF = "%.1f".format(kelvinToDouble.minus(Constants.KELVIN_TO_F) * 9 / 5 + Constants.TEMPERATURE_CONST)
        return "${toF}${0x00B0.toChar()}F"
    }
}
