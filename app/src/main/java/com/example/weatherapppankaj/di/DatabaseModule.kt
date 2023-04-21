package com.example.weatherapppankaj.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapppankaj.database.DatabaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDb(application: Application): DatabaseClient{
        return Room.databaseBuilder(application, DatabaseClient::class.java, "weather.db")
            .fallbackToDestructiveMigration()
            .build()
    }

}