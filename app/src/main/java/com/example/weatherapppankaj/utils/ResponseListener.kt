package com.example.weatherapppankaj.utils

interface ResponseListener {
    fun onLoading()
    fun onSuccess(message:String?)
    fun onError(message: String?)
}