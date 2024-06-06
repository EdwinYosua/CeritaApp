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
import com.edwinyosua.ceritaapp.network.apiresponse.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AppRepository private constructor(
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

    suspend fun getStoriesDetailById(id: String) = apiService.getDetailStories(id)

    suspend fun uploadStories(
        multiBody: MultipartBody.Part,
        reqBodyDesc: RequestBody,
        latReqBody: RequestBody?,
        lonReqBody: RequestBody?
    ): UploadResponse {
        return apiService.uploadImage(
            multiBody,
            reqBodyDesc,
            latReqBody,
            lonReqBody
        )
    }


    companion object {
        @Volatile
        private var instance: AppRepository? = null
        fun getInstance(apiService: ApiService, pref: SettingPreferences): AppRepository =
            instance ?: synchronized(this) {
                instance ?: AppRepository(apiService, pref).also { instance = it }
            }
    }

}