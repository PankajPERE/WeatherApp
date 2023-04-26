package com.example.weatherapppankaj.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.weatherapppankaj.R
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.databinding.ActivitySignUpBinding
import com.example.weatherapppankaj.utils.AppResponse
import com.example.weatherapppankaj.utils.CommonUtils.showToast
import com.example.weatherapppankaj.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val userViewModel:UserViewModel by viewModels()
    lateinit var email:String
    lateinit var pass:String
    lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSignup.setOnClickListener {
            email = binding.edtEmail.text.toString()
            pass = binding.edtPass.text.toString()
            name = binding.edtName.text.toString()

            val user = Users(0,email,pass,name)
            userViewModel.insertUser(user)
        }

        setObserver()
    }

    private fun setObserver() {
        userViewModel.appResponse.observe( this, Observer {
            when(it){
                is AppResponse.Error -> {
                    showToast(this, it.message?:getString(R.string.something_went_wrong))
                }
                is AppResponse.Loading -> {}
                is AppResponse.Success -> {
                    showToast(this, it.data?.toString()?:getString(R.string.something_went_wrong))
                    startWeatherActivity()
                }
            }
        })
    }

    private fun startWeatherActivity() {
        val intent = Intent(this,LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}