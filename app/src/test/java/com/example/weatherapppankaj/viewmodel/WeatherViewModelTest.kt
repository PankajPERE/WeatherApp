package com.example.weatherapppankaj.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapppankaj.getOrAwaitValue
import com.example.weatherapppankaj.model.WeatherResponse
import com.example.weatherapppankaj.repository.WeatherRepository
import com.example.weatherapppankaj.utils.AppResponse
import com.example.weatherapppankaj.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class WeatherViewModelTest {

    private val testDispatcheer = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository:WeatherRepository

    lateinit var viewModel: WeatherViewModel

    @Mock
    lateinit var weatherData:Response<WeatherResponse>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcheer)
        viewModel = WeatherViewModel(repository)
    }

    @Test
    fun testWeatherData_expected_success()= runTest{

        val weatherResponse = WeatherResponse(name= "pankaj")

        Mockito.`when`(repository.getWeatherDataRemote(0.00,0.00,"abc")).thenReturn(weatherResponse)

        viewModel.getWeatherDataRemote(0.00,0.00,"abc")
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.weatherData.getOrAwaitValue()
        Assert.assertTrue(result is Resource.Success)

    }

    @Test
    fun testWeatherData_expected_error()= runTest{

        val weatherResponse = null

        Mockito.`when`(repository.getWeatherDataRemote(0.00,0.00,"abc")).thenReturn(weatherResponse)

        viewModel.getWeatherDataRemote(0.00,0.00,"abc")
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.weatherData.getOrAwaitValue()
        Assert.assertTrue(result is Resource.Error)

    }


    @Test
    fun testWeatherHistoryData_expected_success()= runTest{

        val weatherResponse = null

        Mockito.`when`(repository.getWeatherHistory()).thenReturn(weatherResponse)

        val result = viewModel.getWeatherHistory()
        testDispatcheer.scheduler.advanceUntilIdle()
        Assert.assertEquals(null,result)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}