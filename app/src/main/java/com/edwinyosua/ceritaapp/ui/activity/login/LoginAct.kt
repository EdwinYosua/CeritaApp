package com.edwinyosua.ceritaapp.ui.activity.login

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
import com.edwinyosua.ceritaapp.ui.activity.home.HomeAct

class LoginAct : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
//    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
//    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> {
//        factory
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val loginViewModel: LoginViewModel by viewModels<LoginViewModel> {
            factory
        }


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

            loginViewModel.loginResult.observe(this@LoginAct) { response ->
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
                        val loginIntent = Intent(this@LoginAct, HomeAct::class.java)
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
}