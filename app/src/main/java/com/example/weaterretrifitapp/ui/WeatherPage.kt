package com.example.ui.weaterretrifitapp

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
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.weaterretrifitapp.ui.WeatherMainPartPage
import com.example.weaterretrifitapp.ui.theme.BackGround
import com.example.weaterretrifitapp.ui.theme.Container
import com.example.weaterretrifitapp.ui.theme.Orange
import kotlin.math.roundToInt



@Composable
fun WeatherPage(
    weatherViewModel: WeatherViewModel,
    sharedPreferencesCities: SharedPreferences
) {
    Column(Modifier.fillMaxSize().background(BackGround)){
        WeatherMainPartPage(weatherViewModel,sharedPreferencesCities)
    }
}
