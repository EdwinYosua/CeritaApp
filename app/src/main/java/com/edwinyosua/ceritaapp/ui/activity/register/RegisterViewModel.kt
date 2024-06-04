package com.edwinyosua.ceritaapp.ui.activity.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.network.apiresponse.RegisterResponse
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.repository.AppRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class RegisterViewModel(private val appRepo: AppRepository) : ViewModel() {

    private val _registResult = MutableLiveData<ApiResult<RegisterResponse>>()
    val registResult: LiveData<ApiResult<RegisterResponse>> = _registResult
    fun registerUser(name: String, email: String, pass: String) {
        viewModelScope.launch {
            try {
                _registResult.value = ApiResult.Loading
                val response = appRepo.register(name, email, pass)
                if (response.error != true) {
                    _registResult.value = ApiResult.ApiSuccess(response)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                _registResult.value = ApiResult.ApiError(e.message.toString())
            }
        }
    }
}