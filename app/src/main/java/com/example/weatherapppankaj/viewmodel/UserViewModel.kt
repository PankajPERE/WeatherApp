package com.example.weatherapppankaj.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel(){

    val userData: MutableLiveData<Users> = MutableLiveData()

    fun insertUser(user:Users) = viewModelScope.launch {
        userRepository.insertUser(user)
    }


    fun login(email:String, pass:String) = viewModelScope.launch {
        val response = userRepository.loginUser(email,pass)
        userData.postValue(response)
    }
}