package com.example.weatherapppankaj.utils

import android.content.Context
import android.widget.Toast

object CommonUtils {

    const val ENTER_EMAIL ="Enter email"
    const val ENTER_PASSWORD ="Enter password"
    const val ENTER_NAME ="Enter name"
    const val LOGIN_SUCCESSFUL ="Login successful"
    const val USER_NOT_FOUND ="User not found"


    fun showToast(context: Context, message:String){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

}