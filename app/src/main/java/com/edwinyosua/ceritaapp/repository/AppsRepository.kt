package com.edwinyosua.ceritaapp.repository

import androidx.datastore.dataStore
import com.edwinyosua.ceritaapp.local.SettingPreferences
import com.edwinyosua.ceritaapp.network.ApiResponse.LoginResponse
import com.edwinyosua.ceritaapp.network.ApiService

class AppsRepository private constructor(
    private val apiService: ApiService,
    private val pref: SettingPreferences
) {

    fun getToken() = pref.getLoginToken()

    suspend fun clearToken() = pref.clearLoginToken()

    suspend fun register(name: String, email: String, pass: String) =
        apiService.register(name, email, pass)

    suspend fun login(email: String, pass: String): LoginResponse {
        val client = apiService.login(email, pass)
        if (client.loginResult?.token?.isNotEmpty() == true) {
            pref.saveLoginToken(client.loginResult.token)
        }
        return client
    }

    companion object {
        @Volatile
        private var instance: AppsRepository? = null
        fun getInstance(apiService: ApiService, pref: SettingPreferences): AppsRepository =
            instance ?: synchronized(this) {
                instance ?: AppsRepository(apiService, pref).also { instance = it }
            }
    }

}