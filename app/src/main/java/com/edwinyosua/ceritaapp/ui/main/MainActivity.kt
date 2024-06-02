package com.edwinyosua.ceritaapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.databinding.ActivityMainBinding
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.home.HomeAct
import com.edwinyosua.ceritaapp.ui.login.LoginAct
import com.edwinyosua.ceritaapp.ui.register.RegisterAct

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
                    val startIntent = Intent(this@MainActivity, HomeAct::class.java)
                    startActivity(startIntent)
                    finish()
                } else {
                    loginBtn.isEnabled = true
                    registerBtn.isEnabled = true
                    prgBar.visibility = View.GONE
                }
            }


            registerBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, RegisterAct::class.java))
            }
            loginBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, LoginAct::class.java))
            }
        }
    }
}

