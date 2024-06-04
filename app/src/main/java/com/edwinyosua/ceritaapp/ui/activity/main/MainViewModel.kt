package com.edwinyosua.ceritaapp.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.edwinyosua.ceritaapp.repository.AppRepository

class MainViewModel(private val appRepo: AppRepository) : ViewModel() {
    fun getToken() = appRepo.getToken().asLiveData()
}