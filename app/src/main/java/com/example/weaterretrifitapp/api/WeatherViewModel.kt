package com.example.weaterretrifitapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weaterretrifitapp.api.NetworkResponse.*
import kotlinx.coroutines.launch


public class WeatherViewModel: ViewModel() {
    private val weatherAPI = RetrofitInstance.weatherAPI
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city:String){
        _weatherResult.value=NetworkResponse.Loading
        try {
            viewModelScope.launch {
                val response = weatherAPI.getWeather(city, Constant.apiKey)
                if (response.isSuccessful) {
                    response.body()?.let { it ->
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            }
        }
        catch (e :Exception){
            _weatherResult.value = NetworkResponse.Error("Failed to load data")
        }

    }
}