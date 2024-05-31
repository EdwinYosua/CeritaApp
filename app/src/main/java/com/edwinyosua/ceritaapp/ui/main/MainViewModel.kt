package com.edwinyosua.ceritaapp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.edwinyosua.ceritaapp.repository.AppsRepository

class MainViewModel(private val appRepo: AppsRepository) : ViewModel() {
    fun getToken() = appRepo.getToken().asLiveData()
}