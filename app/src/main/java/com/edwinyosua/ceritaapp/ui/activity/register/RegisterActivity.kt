package com.edwinyosua.ceritaapp.ui.activity.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.edwinyosua.ceritaapp.R
import com.edwinyosua.ceritaapp.databinding.ActivityRegisterBinding
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.ui.ViewModelFactory
import com.edwinyosua.ceritaapp.ui.activity.main.MainActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val registerViewModel: RegisterViewModel by viewModels<RegisterViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.apply {
            btnDaftar.setOnClickListener {

                if (edtName.text?.isEmpty() == true) {
                    edtName.error = getString(R.string.error_nama)
                }
                if (edtEmail.text?.isEmpty() == true) {
                    edtEmail.error = getString(R.string.error_email)
                }
                if (edtPassword.text?.isEmpty() == true) {
                    edtPassword.error = getString(R.string.error_password)
                }
                if (edtName.text?.isNotEmpty() == true
                    && edtEmail.text?.isNotEmpty() == true
                    && edtPassword.text?.isNotEmpty() == true
                ) {
                    registerViewModel.registerUser(
                        edtName.text?.trim().toString(),
                        edtEmail.text?.trim().toString(),
                        edtPassword.text?.trim().toString()
                    )
                }
            }


            registerViewModel.registResult.observe(this@RegisterActivity) { response ->
                when (response) {
                    is ApiResult.ApiError -> {
                        prgBar.visibility = View.GONE
                        showToast(response.error)
                        btnDaftar.isEnabled = true
                    }

                    is ApiResult.ApiSuccess -> {
                        prgBar.visibility = View.GONE
                        showToast(response.data.message.toString())
                        btnDaftar.isEnabled = true
                        val registIntent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(
                            registIntent.addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK
                            )
                        )
                        finish()
                    }

                    ApiResult.Loading -> {
                        prgBar.visibility = View.VISIBLE
                        btnDaftar.isEnabled = false
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        binding.apply {
            ObjectAnimator.ofFloat(appIcon, View.TRANSLATION_X, -30f, 30f).apply {
                duration = 6000
                repeatCount = ObjectAnimator.INFINITE
                repeatMode = ObjectAnimator.REVERSE
            }.start()

            val registerText =
                ObjectAnimator.ofFloat(registerText, View.ALPHA, 1f).setDuration(1000)
            val edtNama = ObjectAnimator.ofFloat(edtName, View.ALPHA, 1f).setDuration(1000)
            val edtEmail = ObjectAnimator.ofFloat(edtEmail, View.ALPHA, 1f).setDuration(1000)
            val edtPass = ObjectAnimator.ofFloat(edtPassword, View.ALPHA, 1f).setDuration(1000)
            val btnDaftar = ObjectAnimator.ofFloat(btnDaftar, View.ALPHA, 1f).setDuration(1000)

            val together = AnimatorSet().apply {
                playTogether(edtNama, edtEmail, edtPass)
            }
            AnimatorSet().apply {
                playSequentially(registerText, together, btnDaftar)
                start()
            }
        }

    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}