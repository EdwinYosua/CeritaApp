package com.edwinyosua.ceritaapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.repository.AppsRepository
import kotlinx.coroutines.launch

class MainViewModel(private val appRepo: AppsRepository) : ViewModel() {


    private val _tokenLogin = MutableLiveData<String>()
    val tokenLogin: LiveData<String> = _tokenLogin


    fun getLoginToke() {
        viewModelScope.launch {
            val token = appRepo.getToken()
            _tokenLogin.value = token.toString()
        }
    }
}