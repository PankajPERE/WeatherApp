package com.example.weatherapppankaj.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.database.entities.Weather

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users): Long

    //checking user exist or not in our db
    @Query("SELECT * FROM Users WHERE email LIKE :email AND password LIKE :password")
    suspend fun loginUser(email: String, password: String): Users

    @Query("DELETE FROM Users")
    fun deleteAll()

}