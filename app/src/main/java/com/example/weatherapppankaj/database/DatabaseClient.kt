package com.example.weatherapppankaj.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapppankaj.database.dao.UserDao
import com.example.weatherapppankaj.database.entities.Users

@Database(entities = [Users::class], version = 1, exportSchema = false)
abstract class DatabaseClient:RoomDatabase() {
        abstract fun getUserDao(): UserDao
    }
