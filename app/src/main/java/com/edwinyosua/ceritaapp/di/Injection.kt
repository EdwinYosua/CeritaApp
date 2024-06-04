package com.edwinyosua.ceritaapp.di

import android.content.Context
import com.edwinyosua.ceritaapp.local.SettingPreferences
import com.edwinyosua.ceritaapp.local.dataStore
import com.edwinyosua.ceritaapp.network.ApiConfig
import com.edwinyosua.ceritaapp.repository.AppRepository

object Injection {

    fun provideRepo(context: Context): AppRepository {
        val pref = SettingPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService(pref)
        return AppRepository.getInstance(apiService, pref)
    }
}