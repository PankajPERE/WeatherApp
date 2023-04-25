package com.example.weatherapppankaj.utils

import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UtilsTest {

    private lateinit var utils: Utils

    @Before
    fun setUp() {
        utils = Utils
    }

    @After
    fun tearDown() {
    }

    @Test
    fun convertDateTime_expected_string_date(){
        val result = utils.convertDate(1682399725)
        assertEquals("10:45 AM", result)
    }

    @Test
    fun convertKelvinTemp_expected_degree_cel(){
        val result = utils.convertKelvinToCel(306.38)
        assertEquals("33.3° C", result)
    }

    @Test
    fun getImageUrl_expected_complete_image_url(){
        val result = utils.getImageUrlByName("50d")
        assertEquals("https://openweathermap.org/img/wn/50d@4x.png", result)
    }
}