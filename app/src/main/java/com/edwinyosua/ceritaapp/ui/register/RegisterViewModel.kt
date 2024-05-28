package com.edwinyosua.ceritaapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.network.ApiResponse.RegisterResponse
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.repository.AppsRepository
import kotlinx.coroutines.launch

class RegisterViewModel(private val appRepo: AppsRepository) : ViewModel() {

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
            } catch (e: Exception) {
                _registResult.value = ApiResult.ApiError(e.message.toString())
            }
        }
    }
}