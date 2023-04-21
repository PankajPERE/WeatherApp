package com.example.weatherapppankaj.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weatherapppankaj.database.entities.Users
import com.example.weatherapppankaj.databinding.ActivitySignUpBinding
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
            Toast.makeText(this,"User Registered",Toast.LENGTH_SHORT).show()
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}