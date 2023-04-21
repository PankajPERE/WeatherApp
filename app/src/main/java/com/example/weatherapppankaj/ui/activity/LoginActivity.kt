package com.example.weatherapppankaj.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.weatherapppankaj.R
import com.example.weatherapppankaj.databinding.ActivityLoginBinding
import com.example.weatherapppankaj.databinding.ActivitySignUpBinding
import com.example.weatherapppankaj.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()
    lateinit var email:String
    lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            email = binding.edtEmail.text.toString()
            pass = binding.edtPass.text.toString()

            userViewModel.login(email, pass)
            Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show()
        }
    }
}