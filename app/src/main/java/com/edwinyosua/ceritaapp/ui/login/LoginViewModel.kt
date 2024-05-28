package com.edwinyosua.ceritaapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edwinyosua.ceritaapp.network.ApiResponse.LoginResponse
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.repository.AppsRepository

class LoginViewModel(private val appRepo: AppsRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<ApiResult<LoginResponse>>()
    val loginResult: LiveData<ApiResult<LoginResponse>> = _loginResult

    fun loginUser(email: String, pass: String) {}

}