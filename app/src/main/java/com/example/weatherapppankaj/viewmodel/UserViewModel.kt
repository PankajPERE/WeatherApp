package com.example.weatherapppankaj.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.repository.UserRepository
import com.example.weatherapppankaj.utils.CommonUtils
import com.example.weatherapppankaj.utils.ResponseListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){

    val userData: MutableLiveData<Users> = MutableLiveData()
    var responseListener: ResponseListener? = null

    fun insertUser(user:Users){
        when{
            user.email.isNullOrEmpty() ->{
                responseListener?.onError(CommonUtils.ENTER_EMAIL)
                return
            }
            user.password.isNullOrEmpty() ->{
                responseListener?.onError(CommonUtils.ENTER_PASSWORD)
                return
            }
            user.name.isNullOrEmpty() ->{
                responseListener?.onError(CommonUtils.ENTER_NAME)
                return
            }
        }

        viewModelScope.launch {
            userRepository.insertUser(user)
            responseListener?.onSuccess("Registration Successful")
            return@launch
        }
    }


    fun login(email:String, pass:String) {

        when{
            email.isNullOrEmpty() ->{
                responseListener?.onError(CommonUtils.ENTER_EMAIL)
                return
            }
            pass.isNullOrEmpty() ->{
                responseListener?.onError(CommonUtils.ENTER_PASSWORD)
                return
            }
        }

        viewModelScope.launch {
            val response = userRepository.loginUser(email,pass)

            response?.let {
                responseListener?.onSuccess(CommonUtils.LOGIN_SUCCESSFUL)
                Log.e("LOGIN",response.email.toString())
                return@launch
            } ?: run{
                responseListener?.onError(CommonUtils.USER_NOT_FOUND)
                return@launch
            }


        }
    }
}