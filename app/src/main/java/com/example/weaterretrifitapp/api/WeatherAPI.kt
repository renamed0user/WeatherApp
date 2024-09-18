package com.example.weaterretrifitapp.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/data/2.5/weather?")
    suspend fun getWeather(
        @Query("q")apikey:String,
        @Query("appid")city:String
    ): Response<WeatherModel>
}