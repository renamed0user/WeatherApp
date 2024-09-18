package com.example.weaterretrifitapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.ui.weaterretrifitapp.WeatherPage
import com.example.weaterretrifitapp.api.WeatherViewModel
import com.example.weaterretrifitapp.ui.theme.WeaterRetrifitAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        val sharedPreferencesCities: SharedPreferences = this.getSharedPreferences("saved_cities", Context.MODE_PRIVATE)
        //sharedPreferencesCities.edit().putString("cities", "").apply()
        setContent {
            WeaterRetrifitAppTheme {
                WeatherPage(weatherViewModel,sharedPreferencesCities)
            }
        }
    }
}
