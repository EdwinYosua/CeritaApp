package com.edwinyosua.ceritaapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.edwinyosua.ceritaapp.local.SettingPreferences
import com.edwinyosua.ceritaapp.network.ApiService
import com.edwinyosua.ceritaapp.network.StoriesPagingSources
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem
import com.edwinyosua.ceritaapp.network.apiresponse.LoginResponse

class AppsRepository private constructor(
    private val apiService: ApiService,
    private val pref: SettingPreferences
) {

    fun getAllStories(): LiveData<PagingData<ListStoryItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3
            ),
            pagingSourceFactory = {
                StoriesPagingSources(apiService)
            }
        ).liveData
    }

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


//    suspend fun getStories(token: String) = apiService.getStories(token)


    companion object {
        @Volatile
        private var instance: AppsRepository? = null
        fun getInstance(apiService: ApiService, pref: SettingPreferences): AppsRepository =
            instance ?: synchronized(this) {
                instance ?: AppsRepository(apiService, pref).also { instance = it }
            }
    }

}