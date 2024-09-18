package com.example.weaterretrifitapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val url = "https://api.openweathermap.org/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherAPI : WeatherAPI = getInstance().create(WeatherAPI::class.java)
}