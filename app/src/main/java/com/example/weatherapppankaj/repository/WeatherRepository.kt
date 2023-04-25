package com.example.weatherapppankaj.repository

import androidx.lifecycle.LiveData
import com.example.weatherapppankaj.database.DatabaseClient
import com.example.weatherapppankaj.database.entities.Weather
import com.example.weatherapppankaj.model.WeatherResponse
import com.example.weatherapppankaj.network.ApiService
import com.example.weatherapppankaj.utils.CommonUtils
import com.example.weatherapppankaj.utils.Resource
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService, private val db: DatabaseClient) {

    suspend fun getWeatherDataRemote(lat:Double,lon:Double, apiKey:String): WeatherResponse? {
        val response = apiService.getCurrentWeather(lat,lon,apiKey)
        return if (response.isSuccessful){
            response.body()
        }else{
            null
        }
    }

    suspend fun addWeatherData(weather: Weather) = db.getWeatherDao().insertWeatherData(weather)

    fun getWeatherHistory() : LiveData<List<Weather>> = db.getWeatherDao().getWeatherHistory()
}