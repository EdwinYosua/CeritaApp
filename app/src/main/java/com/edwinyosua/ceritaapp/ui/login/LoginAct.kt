package com.edwinyosua.ceritaapp.ui.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.databinding.ActivityLoginBinding
import com.edwinyosua.ceritaapp.ui.ViewModelFactory

class LoginAct : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}