package com.edwinyosua.ceritaapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.repository.AppsRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val appRepo: AppsRepository) : ViewModel() {



    fun logoutUser() {
        viewModelScope.launch {
            appRepo.clearToken()
        }
    }

}