package com.edwinyosua.ceritaapp.ui.register

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
import com.edwinyosua.ceritaapp.ui.main.MainActivity

class RegisterAct : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val registerViewModel: RegisterViewModel by viewModels<RegisterViewModel> {
            factory
        }



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


            registerViewModel.registResult.observe(this@RegisterAct) { response ->
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
                        startActivity(Intent(this@RegisterAct, MainActivity::class.java))
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

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}