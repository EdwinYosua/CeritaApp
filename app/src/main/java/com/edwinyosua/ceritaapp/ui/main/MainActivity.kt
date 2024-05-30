package com.edwinyosua.ceritaapp.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.databinding.ActivityMainBinding
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.home.HomeAct
import com.edwinyosua.ceritaapp.ui.login.LoginAct
import com.edwinyosua.ceritaapp.ui.register.RegisterAct

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

//    override fun onStart() {
//        super.onStart()
//
//        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
//        val mainViewModel: MainViewModel by viewModels<MainViewModel> {
//            factory
//        }
//        mainViewModel.tokenLogin.observe(this@MainActivity) { token ->
//            if (token.isNotEmpty()) {
//                startActivity(Intent(this@MainActivity, HomeAct::class.java))
//                finish()
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            registerBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterAct::class.java))
            }
            loginBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginAct::class.java))
            }
        }
    }
}

