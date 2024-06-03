package com.edwinyosua.ceritaapp.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.network.apiresponse.LoginResponse
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.repository.AppsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(
    private val appRepo: AppsRepository,
) : ViewModel() {

    private val _loginResult = MutableLiveData<ApiResult<LoginResponse>>()
    val loginResult: LiveData<ApiResult<LoginResponse>> = _loginResult


    fun loginUser(email: String, pass: String) {
        viewModelScope.launch {
            try {
                _loginResult.value = ApiResult.Loading
                val response = appRepo.login(email, pass)
                if (response.loginResult?.token?.isNotEmpty() == true) {
                    _loginResult.value = ApiResult.ApiSuccess(response)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                _loginResult.value = ApiResult.ApiError(e.message().toString())
            }
        }
    }

}