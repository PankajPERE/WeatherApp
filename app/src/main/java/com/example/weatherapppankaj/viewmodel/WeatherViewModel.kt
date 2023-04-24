package com.example.weatherapppankaj.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapppankaj.database.entities.Weather
import com.example.weatherapppankaj.model.WeatherResponse
import com.example.weatherapppankaj.repository.WeatherRepository
import com.example.weatherapppankaj.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) : ViewModel(){

    val weatherData: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()

    fun getWeatherDataRemote( lat:Double, lon:Double, apiKey:String) = viewModelScope.launch {
        weatherData.postValue(Resource.Loading())
        val response = weatherRepository.getWeatherDataRemote(lat,lon,apiKey)
        weatherData.postValue(handleWeatherDataResponse(response))
    }

    private fun handleWeatherDataResponse(response: Response<WeatherResponse>): Resource<WeatherResponse>? {
        if (response.isSuccessful){
            response.body()?.let { resultResponse->

                val weather = Weather(
                    temp = resultResponse.main.temp,
                    imgUrl = resultResponse.weather[0].icon,
                    city = resultResponse.name,
                    country = resultResponse.sys.country,
                    time = resultResponse.dt,
                    sunrise = resultResponse.sys.sunrise,
                    sunset = resultResponse.sys.sunset
                )
                viewModelScope.launch {
                    weatherRepository.addWeatherData(weather)
                }
                return Resource.Success(resultResponse)
            }
        }

        return Resource.Error(response.message())
    }

}