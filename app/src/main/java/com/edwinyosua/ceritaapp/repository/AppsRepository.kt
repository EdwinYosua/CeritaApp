package com.edwinyosua.ceritaapp.repository

import com.edwinyosua.ceritaapp.network.ApiService

class AppsRepository private constructor(
    private val apiService: ApiService
) {

    suspend fun register(name: String, email: String, pass: String) =
        apiService.register(name, email, pass)

    companion object {
        @Volatile
        private var instance: AppsRepository? = null
        fun getInstance(apiService: ApiService): AppsRepository = instance ?: synchronized(this) {
            instance ?: AppsRepository(apiService).also { instance = it }
        }
    }

}