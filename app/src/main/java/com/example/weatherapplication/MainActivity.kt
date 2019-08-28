package com.example.weatherapplication

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : Activity(), MvpInterface.View {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var presenter: WeatherPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = WeatherPresenter(this)
        presenter?.requestDataFromServer()

        val mCityNames = mutableListOf<String>()
        val mTemperatures = mutableListOf<String>()

        mCityNames.add("SanFrancisco")
        mCityNames.add("NewYork")
        mTemperatures.add("75")
        mTemperatures.add("85")

        viewManager = LinearLayoutManager(this)
        viewAdapter = WeatherAdapter(mCityNames, mTemperatures)

        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }
}
