package com.example.weatherapppankaj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.repository.UserRepository
import com.example.weatherapppankaj.utils.AppResponse
import com.example.weatherapppankaj.utils.Resource
import com.example.weatherapppankaj.utils.CommonUtils
import com.example.weatherapppankaj.utils.ResponseListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){

    private val _appResponse = MutableLiveData<AppResponse>()
    val appResponse: LiveData<AppResponse>
        get() = _appResponse

    fun insertUser(user:Users){
        when{
            !isValidEmail(user.email)->{
                _appResponse.postValue(AppResponse.Error(CommonUtils.ENTER_EMAIL))
                return
            }
            user.password.isNullOrEmpty() ->{
                _appResponse.postValue(AppResponse.Error(CommonUtils.ENTER_PASSWORD))
                return
            }
            user.name.isNullOrEmpty() ->{
                _appResponse.postValue(AppResponse.Error(CommonUtils.ENTER_NAME))
                return
            }
        }

        viewModelScope.launch {
            userRepository.insertUser(user)
            _appResponse.postValue(AppResponse.Success(CommonUtils.REGISTRATION_SUCCESSFUL))

            return@launch
        }
    }


    fun login(email:String?, pass:String?) {

        when{
            !isValidEmail(email) ->{
                _appResponse.postValue(AppResponse.Error(CommonUtils.ENTER_EMAIL))
                return
            }
            pass.isNullOrEmpty() ->{
                _appResponse.postValue(AppResponse.Error(CommonUtils.ENTER_PASSWORD))

                return
            }
        }

        viewModelScope.launch {
            val response = userRepository.loginUser(email?:"",pass?:"")

            response?.let {
                _appResponse.postValue(AppResponse.Success(CommonUtils.LOGIN_SUCCESSFUL))
                return@launch
            } ?: run{
                _appResponse.postValue(AppResponse.Error(CommonUtils.USER_NOT_FOUND))
                return@launch
            }


        }
    }

    fun isValidEmail(text: String?): Boolean {
        return text?.trim()?.isNotEmpty() == true && CommonUtils.emailPattern.toRegex().matches(text)
    }
}