package com.example.weatherapppankaj.repository

import androidx.lifecycle.LiveData
import com.example.weatherapppankaj.database.DatabaseClient
import com.example.weatherapppankaj.database.entities.Weather
import com.example.weatherapppankaj.network.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val apiService: ApiService, private val db: DatabaseClient) {

    suspend fun getWeatherDataRemote(lat:Double,lon:Double, apiKey:String) = apiService.getCurrentWeather(lat,lon,apiKey)

    suspend fun addWeatherData(weather: Weather) = db.getWeatherDao().insertWeatherData(weather)

}