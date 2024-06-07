package com.edwinyosua.ceritaapp.ui.activity.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.R
import com.edwinyosua.ceritaapp.databinding.ActivityLoginBinding
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.activity.home.HomeActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.apply {
            btnLogin.setOnClickListener {
                if (edtEmail.text?.isEmpty() == true) {
                    edtEmail.error = getString(R.string.error_email)
                }
                if (edtPassword.text?.isEmpty() == true) {
                    edtPassword.error = getString(R.string.error_password)
                }
                if (edtEmail.text?.isNotEmpty() == true &&
                    edtPassword.text?.isNotEmpty() == true
                ) {
                    loginViewModel.loginUser(
                        edtEmail.text?.trim().toString(),
                        edtPassword.text?.trim().toString()
                    )
                }
            }

            loginViewModel.loginResult.observe(this@LoginActivity) { response ->
                when (response) {
                    is ApiResult.ApiError -> {
                        prgBar.visibility = View.GONE
                        showToast(response.error)
                        btnLogin.isEnabled = true
                    }

                    is ApiResult.ApiSuccess -> {
                        prgBar.visibility = View.GONE
                        showToast(response.data.message.toString())
                        btnLogin.isEnabled = true
                        val loginIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(
                            loginIntent.addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                            )
                        )
                        finish()
                    }

                    ApiResult.Loading -> {
                        prgBar.visibility = View.VISIBLE
                        btnLogin.isEnabled = false
                    }
                }
            }
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.appIcon, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatMode = ObjectAnimator.REVERSE
            repeatCount = ObjectAnimator.INFINITE
        }.start()

        binding.apply {
            val loginText = ObjectAnimator.ofFloat(loginTxt, View.ALPHA, 1f).setDuration(1000)
            val emailEdt = ObjectAnimator.ofFloat(edtEmail, View.ALPHA, 1f).setDuration(1000)
            val passEdt = ObjectAnimator.ofFloat(edtPassword, View.ALPHA, 1f).setDuration(1000)
            val loginBtn = ObjectAnimator.ofFloat(btnLogin, View.ALPHA, 1f).setDuration(1000)

            val together = AnimatorSet().apply {
                playTogether(emailEdt, passEdt)
            }

            AnimatorSet().apply {
                playSequentially(loginText, together, loginBtn)
                start()
            }
        }

    }
}