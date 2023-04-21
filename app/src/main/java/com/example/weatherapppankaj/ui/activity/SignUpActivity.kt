package com.example.weatherapppankaj.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.databinding.ActivitySignUpBinding
import com.example.weatherapppankaj.utils.CommonUtils.showToast
import com.example.weatherapppankaj.utils.ResponseListener
import com.example.weatherapppankaj.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity(), ResponseListener {

    private lateinit var binding: ActivitySignUpBinding
    private val userViewModel:UserViewModel by viewModels()
    lateinit var email:String
    lateinit var pass:String
    lateinit var name:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel.responseListener = this


        binding.btnSignup.setOnClickListener {
            email = binding.edtEmail.text.toString()
            pass = binding.edtPass.text.toString()
            name = binding.edtName.text.toString()

            val user = Users(0,email,pass,name)
            userViewModel.insertUser(user)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onLoading() {
        TODO("Not yet implemented")
    }

    override fun onSuccess(message: String?) {
        showToast(this,message!!)
        startWeatherActivity()
    }

    private fun startWeatherActivity() {
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onError(message: String?) {
        showToast(this,message!!)
    }


}