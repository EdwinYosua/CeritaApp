package com.edwinyosua.ceritaapp.ui.activity.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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

        playAnimation()

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

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.appIcon, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val login = ObjectAnimator.ofFloat(binding.loginBtn, View.ALPHA, 1f).setDuration(1000)
        val signUp = ObjectAnimator.ofFloat(binding.registerBtn, View.ALPHA, 1f).setDuration(1000)
        val title = ObjectAnimator.ofFloat(binding.welcomeTxt, View.ALPHA, 1f).setDuration(1000)

        val together = AnimatorSet().apply {
            playTogether(login, signUp)
        }
        AnimatorSet().apply {
            playSequentially(title, together)
            start()
        }
    }
}

