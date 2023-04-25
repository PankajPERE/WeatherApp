package com.example.weatherapppankaj

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapppankaj.database.DatabaseClient
import com.example.weatherapppankaj.database.dao.WeatherDao
import com.example.weatherapppankaj.database.entities.Weather
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class TestWeatherDao {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var databaseClient: DatabaseClient
    lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseClient = Room.inMemoryDatabaseBuilder(
            context, DatabaseClient::class.java).allowMainThreadQueries().build()
        weatherDao = databaseClient.getWeatherDao()
    }


    @Test
    fun insertWeatherData_expected_single_weather_data() = runBlocking{
        val weather = Weather(
            303.24,
            "50d",
            "Nagpur",
            "In",
            "25-04-2023",
            1682295482,
            1682295482
        )
        weatherDao.insertWeatherData(weather)

        val result = weatherDao.getWeatherHistory().getOrAwaitValue()
        Assert.assertEquals(1,result.size)
        Assert.assertEquals("Nagpur",result[0].city)
    }

    @Test
    fun getWeatherData_expected_single_weather_data() = runBlocking{
        val weather = Weather(
            303.24,
            "50d",
            "Nagpur",
            "In",
            "25-04-2023",
            1682295482,
            1682295482
        )
        weatherDao.insertWeatherData(weather)

        val result = weatherDao.getWeatherHistory().getOrAwaitValue()
        Assert.assertEquals(1,result.size)

    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        databaseClient.close()
    }
}