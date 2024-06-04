package com.edwinyosua.ceritaapp.ui.activity.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.network.apiresponse.ListStoryItem
import com.edwinyosua.ceritaapp.repository.AppRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetailViewModel(private val appRepo: AppRepository) : ViewModel() {

    private var _storiesResponse = MutableLiveData<ApiResult<ListStoryItem>>()
    val storiesResponse = _storiesResponse

    fun getStoriesDetailById(id: String) {
        viewModelScope.launch {
            try {
                _storiesResponse.value = ApiResult.Loading
                val response = appRepo.getStoriesDetailById(id)
                if (!response.error) {
                    _storiesResponse.value = ApiResult.ApiSuccess(response.story)
                }
            } catch (e: HttpException) {
                e.printStackTrace()
                _storiesResponse.value = ApiResult.ApiError(e.message())
            }
        }
    }
}