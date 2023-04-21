package com.example.weatherapppankaj.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Database(entities = [], version = 1)
abstract class DatabaseClient : RoomDatabase() {
        companion object {
            private var INSTANCE: DatabaseClient? = null

            fun buildDatabase(pass: String, context: Context): DatabaseClient {

                if (INSTANCE == null) {
                    val supportFactory = SupportFactory(SQLiteDatabase.getBytes(pass.toCharArray()))

                    INSTANCE =
                        Room.databaseBuilder(context, DatabaseClient::class.java, "Weather")
                            .openHelperFactory(supportFactory).fallbackToDestructiveMigration().build()
                }
                return INSTANCE as DatabaseClient
            }
        }

        abstract fun getDao(): UserDao
    }
