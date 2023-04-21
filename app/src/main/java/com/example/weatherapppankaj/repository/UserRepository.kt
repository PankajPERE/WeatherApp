package com.example.weatherapppankaj.repository


import com.example.weatherapppankaj.database.DatabaseClient
import com.example.weatherapppankaj.database.entities.Users
import javax.inject.Inject

class UserRepository @Inject constructor(private val databaseClient: DatabaseClient) {

    suspend fun insertUser(user: Users) = databaseClient.getUserDao().insertUser(user)

    suspend fun loginUser(email:String, password:String) = databaseClient.getUserDao().loginUser(email,password)

}