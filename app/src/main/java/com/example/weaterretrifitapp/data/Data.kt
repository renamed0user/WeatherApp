package com.example.weaterretrifitapp.data

import android.annotation.SuppressLint
import android.content.SharedPreferences

object Data {
    fun addCity(name:String,sharedPreferencesCities: SharedPreferences){
        val info = sharedPreferencesCities.getString("cities", null)
        val editor = sharedPreferencesCities.edit()
        if(info==""){
            editor.putString("cities", name)
        }
        else editor.putString("cities", info+"_"+name)
        editor.apply()
    }

    fun getCities(sharedPreferencesCities: SharedPreferences) : List<String>? {
        val name = sharedPreferencesCities.getString("cities", null)
        if(!name.isNullOrEmpty()){
            if(name.first()=='_'){

                return name.removePrefix("_").split("_")
            }
        }
        return name?.split("_")
    }

    @SuppressLint("CommitPrefEdits")
    fun deleteCity(sharedPreferencesCities: SharedPreferences, city:String){
        val old = sharedPreferencesCities.getString("cities", null)?.split("_")
        val new = old?.filter { it!=city }
        val editor = sharedPreferencesCities.edit()
        editor.putString("cities", new?.joinToString("_"))
        editor.apply()
    }

    fun setLastCity(sharedPreferencesLastCity: SharedPreferences, city:String){
        val editor = sharedPreferencesLastCity.edit()
        editor.putString("last_city", city)
        editor.apply()
    }
    fun getLastCity(sharedPreferencesLastCity: SharedPreferences): String? {
        val name = sharedPreferencesLastCity.getString("last_city", null)
        return name
    }


}