package com.example.weaterretrifitapp.ui

import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.weaterretrifitapp.R
import com.example.weaterretrifitapp.api.NetworkResponse
import com.example.weaterretrifitapp.api.WeatherModel
import com.example.weaterretrifitapp.api.WeatherViewModel
import com.example.weaterretrifitapp.data.Data
import com.example.weaterretrifitapp.ui.theme.BackGround
import com.example.weaterretrifitapp.ui.theme.Container
import com.example.weaterretrifitapp.ui.theme.Orange
import kotlin.math.roundToInt



@Composable
fun WeatherMainPartPage(
    weatherViewModel: WeatherViewModel,
    sharedPreferencesCities: SharedPreferences
) {
    val citiesLocal = remember {
        mutableStateOf(Data.getCities(sharedPreferencesCities))
    }
    var city by remember { mutableStateOf("") }
    val weatherResult= weatherViewModel.weatherResult.observeAsState()
    val context = LocalContext.current

    Column(
        Modifier.fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center){
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(color = Color.White),
                value = city,
                onValueChange ={
                    city=it
                },
                label = {
                    Text(text = "Search for any location", color = Color.White)
                }
            )
            IconButton(onClick = {
                try {
                    weatherViewModel.getData(city.filter { it != ' ' })
                }
                catch (e:Exception){
                    Toast.makeText(context,"Incorrect input", Toast.LENGTH_SHORT).show()
                }
            }) {
                Icon(painter = painterResource(R.drawable.baseline_search_24),
                    contentDescription = "search button", tint = Color.White)
            }
        }
        Spacer(Modifier.height(20.dp))



        LazyRow(
            Modifier.fillMaxWidth().defaultMinSize(minHeight = 40.dp).padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            citiesLocal?.let {
                items(it.value?.size ?: 0){
                    if(citiesLocal?.value!!.get(it)!="") {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Container
                            ), modifier = Modifier.padding(5.dp, 0.dp).clickable {
                                try {
                                    weatherViewModel.getData(citiesLocal?.value!!.get(it).toString())
                                }
                                catch (e:Exception){
                                    Toast.makeText(context,"Incorrect input", Toast.LENGTH_SHORT).show()
                                }
                            }
                        ) {
                            Text(
                                text = citiesLocal!!.value!!.get(it), color = Orange, modifier = Modifier
                                    .padding(10.dp, 5.dp)
                            )
                        }
                    }
                }
            }
        }



        when(val result = weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(result.data, sharedPreferencesCities,citiesLocal,{citiesLocal.value=
                    citiesLocal.value?.plus(it)
                }){citiesLocal.value=
                    citiesLocal.value?.minus(it)}
            }
            null -> {}
        }
    }


}


@Composable
fun WeatherDetails(
    data: WeatherModel,
    sharedPreferencesCities: SharedPreferences,
    citiesLocal: MutableState<List<String>?>,
    addCity: (y:String) -> Unit,
    removeCity: (y:String) -> Unit
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(
                    painter = painterResource(R.drawable.baseline_location_pin_24),
                    contentDescription = "Location icon",
                    modifier = Modifier.size(30.dp), tint = Orange
                )

                Text(text = data.name, fontSize = 30.sp, color = Orange)
                Spacer(Modifier.width(8.dp))
                Text(text = data.sys.country, fontSize = 18.sp, color = Color.Yellow)
            }
            IconButton(onClick = {
            }) {
                var color by remember {
                    if(citiesLocal.value!!.contains(data.name)){
                        mutableStateOf(Color.Black)
                    }
                    else {
                        mutableStateOf(Color.White)
                    }
                }
                Icon(
                    painter = painterResource(R.drawable.baseline_bookmark_24),
                    contentDescription = "save", tint = color,
                    modifier = Modifier.size(30.dp).clickable {
                        if(color== Color.White){
                            Data.addCity(data.name,sharedPreferencesCities)
                            addCity(data.name)
                            color = Color.Black
                        }
                        else {
                            if(citiesLocal.value!!.contains(data.name)){
                                Data.deleteCity(sharedPreferencesCities,data.name)
                                removeCity(data.name)
                                color = Color.White
                            }
                        }
                    }
                )
            }

        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = " ${(data.main.temp - 273.15).roundToInt()} ° c",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = Orange
        )

        AsyncImage(
            modifier = Modifier.size(160.dp),
            model = "https://openweathermap.org/img/w/${data.weather[0].icon}.png",
            contentDescription = "Condition item",
            imageLoader = ImageLoader(context)
        )
        Text(
            text = " ${data.weather[0].description}",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Yellow
        )
        Spacer(Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Container
            )
        ) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherShowKeyValue(
                        (data.main.feels_like - 273.15).roundToInt().toString(),
                        "Feels like"
                    )
                    WeatherShowKeyValue(data.wind.speed.toString(), "Wind Speed")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherShowKeyValue(data.main.pressure.toString(), "Pressure")
                    WeatherShowKeyValue(data.main.humidity.toString(), "Humidity")
                }
            }
        }
    }
}


@Composable
fun WeatherShowKeyValue(value:String, key:String){
    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BackGround)
        Text(text = key, fontWeight = FontWeight.SemiBold, color = Orange)

    }
}