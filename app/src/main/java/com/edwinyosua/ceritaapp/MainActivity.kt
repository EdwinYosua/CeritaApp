package com.edwinyosua.ceritaapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.databinding.ActivityMainBinding
import com.edwinyosua.ceritaapp.ui.login.LoginAct
import com.edwinyosua.ceritaapp.ui.register.RegisterAct

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
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