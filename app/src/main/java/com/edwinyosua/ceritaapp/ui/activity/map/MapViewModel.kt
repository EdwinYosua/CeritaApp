package com.edwinyosua.ceritaapp.ui.activity.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem
import com.edwinyosua.ceritaapp.repository.AppRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MapViewModel(private val appRepo: AppRepository) : ViewModel() {

    private val _apiResponse = MutableLiveData<ApiResult<List<ListStoryItem>>>()
    val apiResponse: LiveData<ApiResult<List<ListStoryItem>>> = _apiResponse


    fun getStoriesWithLocation() {
        viewModelScope.launch {
            try {
                _apiResponse.value = ApiResult.Loading
                val client = appRepo.getStoriesWithLocation()
                if (client.error != true || client.listStory.isNotEmpty()) {
                    _apiResponse.value = ApiResult.ApiSuccess(client.listStory)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                Log.e("MapViewModel", "OnFailure = ${e.message()}")
                _apiResponse.value = ApiResult.ApiError(e.message().toString())
            }
        }
    }


}