package com.example.weatherapppankaj.utils

sealed class AppResponse() {
    class Success(val data: Any? = null) : AppResponse()
    class Error(val message: String? = null) : AppResponse()
    class Loading : AppResponse()
}