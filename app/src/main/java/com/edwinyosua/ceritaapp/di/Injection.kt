package com.edwinyosua.ceritaapp.di

import android.content.Context
import com.edwinyosua.ceritaapp.network.ApiConfig
import com.edwinyosua.ceritaapp.repository.AppsRepository

object Injection {

    fun provideRepo(context: Context): AppsRepository {
        val apiService = ApiConfig.getApiService()
        return AppsRepository.getInstance(apiService)
    }
}