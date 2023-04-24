package com.example.weatherapppankaj.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.weatherapppankaj.databinding.ActivityLoginBinding
import com.example.weatherapppankaj.utils.CommonUtils.showToast
import com.example.weatherapppankaj.utils.ResponseListener
import com.example.weatherapppankaj.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), ResponseListener {

    private lateinit var binding: ActivityLoginBinding
    private val userViewModel: UserViewModel by viewModels()
    lateinit var email:String
    lateinit var pass:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userViewModel.responseListener = this

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            email = binding.edtEmail.text.toString()
            pass = binding.edtPass.text.toString()

            userViewModel.login(email, pass)
        }
    }

    override fun onLoading() {
    }

    override fun onSuccess(message: String?) {
        showToast(this, message!!)
        startWeatherActivity()
    }

    private fun startWeatherActivity() {
        val intent = Intent(this,HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onError(message: String?) {
        showToast(this, message!!)
    }
}