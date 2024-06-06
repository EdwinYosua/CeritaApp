package com.edwinyosua.ceritaapp.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.databinding.ActivityMainBinding
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.activity.home.HomeActivity
import com.edwinyosua.ceritaapp.ui.activity.login.LoginActivity
import com.edwinyosua.ceritaapp.ui.activity.register.RegisterActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val mainViewModel: MainViewModel by viewModels<MainViewModel> {
            factory
        }


        binding.apply {
            mainViewModel.getToken().observe(this@MainActivity) { token ->
                if (token != null) {
                    val startIntent = Intent(this@MainActivity, HomeActivity::class.java)
                    startActivity(startIntent)
                    finish()
                } else {
                    loginBtn.isEnabled = true
                    registerBtn.isEnabled = true
                    prgBar.visibility = View.GONE
                }
            }


            registerBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            }
            loginBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            }
        }
    }
}

