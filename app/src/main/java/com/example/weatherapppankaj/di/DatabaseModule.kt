package com.example.weatherapppankaj.di

import android.app.Application
import androidx.room.Room
import com.example.weatherapppankaj.BuildConfig
import com.example.weatherapppankaj.database.DatabaseClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDb(application: Application): DatabaseClient{

        val supportFactory = SupportFactory(SQLiteDatabase.getBytes(BuildConfig.DB_PASS.toCharArray()))

        return Room.databaseBuilder(application, DatabaseClient::class.java, "weather.db")
            .openHelperFactory(supportFactory)
            .fallbackToDestructiveMigration()
            .build()
    }

}