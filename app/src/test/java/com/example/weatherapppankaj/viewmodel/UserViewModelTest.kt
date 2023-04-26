package com.example.weatherapppankaj.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.getOrAwaitValue
import com.example.weatherapppankaj.repository.UserRepository
import com.example.weatherapppankaj.utils.AppResponse
import com.nhaarman.mockitokotlin2.any
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserViewModelTest {

    private val testDispatcheer = StandardTestDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: UserRepository
    lateinit var viewModel:UserViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcheer)
        viewModel = UserViewModel(repository)
    }

    @Test
    fun testInsert_user_expected_successful()= runTest{

        val user = Users(0,"pankaj@gmail.com","pankaj","Pankaj")

        Mockito.`when`(repository.insertUser(any())).thenReturn(1)
        viewModel.insertUser(user)
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Success)
    }


    @Test
    fun testInsert_user_invalid_emailId()= runTest{

        val user = Users(0,"pankajgmail","pankaj","Pankaj")

        Mockito.`when`(repository.insertUser(any())).thenReturn(1)
        viewModel.insertUser(user)
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Error)
    }



    @Test
    fun testInsert_user_invalid_password()= runTest{

        val user = Users(0,"pankaj@gmail.com","","Pankaj")

        Mockito.`when`(repository.insertUser(any())).thenReturn(1)
        viewModel.insertUser(user)
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Error)
    }

    @Test
    fun testInsert_user_invalid_name()= runTest{

        val user = Users(0,"pankaj@gmail.com","pankaj","")

        Mockito.`when`(repository.insertUser(any())).thenReturn(1)
        viewModel.insertUser(user)
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Error)
    }


    @Test
    fun validateValidEmail_expectedTrue() {

        val result = viewModel.isValidEmail("pankaj@gmail.com")
        assertEquals(true, result)
    }

    @Test
    fun validateInvalidEmail_expectedFalse() {
        val result = viewModel.isValidEmail("pankajgmail.com")
        assertEquals(false, result)
        val result1 = viewModel.isValidEmail("pankaj@gmailcom")
        assertEquals(false, result1)
    }


    @Test
    fun testLogin_expected_successful()= runTest{

        val user = Users(0,"pankaj@gmail.com","pankaj","Pankaj")

        Mockito.`when`(repository.loginUser("pankaj@gmail.com","pankaj")).thenReturn(user)
        val viewModel = UserViewModel(repository)
        viewModel.login("pankaj@gmail.com","pankaj")
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Success)
    }
    @Test
    fun testLogin_expected_error()= runTest{

        val user = null

        Mockito.`when`(repository.loginUser("pankaj@gmail.com","pankaj")).thenReturn(user)
        val viewModel = UserViewModel(repository)
        viewModel.login("pankaj@gmail.com","pankaj")
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Error)
    }

    @Test
    fun testLoginInvalidEmail_expected_error()= runTest{

        val user = Users(0,"pankajgmail.com","pankaj","Pankaj")

        Mockito.`when`(repository.loginUser("pankajgmail.com","pankaj")).thenReturn(user)
        val viewModel = UserViewModel(repository)
        viewModel.login("pankajgmail.com","pankaj")
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Error)
    }

    @Test
    fun testLoginInvalidPass_expected_error()= runTest{

        val user = Users(0,"pankaj@gmail.com","","Pankaj")

        Mockito.`when`(repository.loginUser("pankaj@gmail.com","")).thenReturn(user)
        val viewModel = UserViewModel(repository)
        viewModel.login("pankaj@gmail.com","")
        testDispatcheer.scheduler.advanceUntilIdle()
        val result = viewModel.appResponse.getOrAwaitValue()
        assertTrue(result is AppResponse.Error)
    }





    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}