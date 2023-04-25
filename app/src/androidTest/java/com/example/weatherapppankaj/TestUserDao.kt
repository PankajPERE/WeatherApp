package com.example.weatherapppankaj

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.weatherapppankaj.database.DatabaseClient
import com.example.weatherapppankaj.database.dao.UserDao
import com.example.weatherapppankaj.database.entities.Users
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class TestUserDao {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var databaseClient: DatabaseClient
    lateinit var userDao: UserDao



    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        databaseClient = Room.inMemoryDatabaseBuilder(
            context, DatabaseClient::class.java).allowMainThreadQueries().build()
        userDao = databaseClient.getUserDao()
    }

    @Test
    fun writeUserAndReadInList() = runBlocking{
        val user = Users(
            1,
            "pankaj@gmail.com",
            "pankaj",
            "Pankaj"
        )
        userDao.insertUser(user)
        val result = userDao.loginUser("pankaj@gmail.com","pankaj")
        Assert.assertEquals("pankaj@gmail.com",result?.email)
    }

    @Test
    fun testInvalidUser_expected_null() = runBlocking{
        val user = Users(
            1,
            "pankaj@gmail.com",
            "pankaj",
            "Pankaj"
        )
        userDao.insertUser(user)
        val result = userDao.loginUser("pankajbhisikar@gmail.com","pankaj")
        Assert.assertEquals(null , result)
    }

    @Test
    fun testDeleteUser() = runBlocking{
        val user = Users(
            1,
            "pankaj@gmail.com",
            "pankaj",
            "Pankaj"
        )
        userDao.insertUser(user)
        val result = userDao.loginUser("pankaj@gmail.com","pankaj")
        Assert.assertEquals("pankaj@gmail.com",result?.email)
        userDao.deleteAll()
        val result1 = userDao.loginUser("pankajbhisikar@gmail.com","pankaj")
        Assert.assertEquals(null , result1)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        databaseClient.close()
    }


}