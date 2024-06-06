package com.edwinyosua.ceritaapp.ui.activity.addmenu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.edwinyosua.ceritaapp.network.ApiResult
import com.edwinyosua.ceritaapp.network.apiresponse.UploadResponse
import com.edwinyosua.ceritaapp.repository.AppRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class AddMenuViewModel(private val appRepo: AppRepository) : ViewModel() {

    private val _uploadResponse = MutableLiveData<ApiResult<UploadResponse>>()
    val uploadResponse: MutableLiveData<ApiResult<UploadResponse>> = _uploadResponse

    fun addStories(
        multiBody: MultipartBody.Part,
        reqBodyDesc: RequestBody,
        latRequestBody: RequestBody?,
        lonRequestBody: RequestBody?
    ) {
        viewModelScope.launch {
            _uploadResponse.value = ApiResult.Loading
            try {
                _uploadResponse.value = ApiResult.ApiSuccess(
                    appRepo.uploadStories(
                        multiBody,
                        reqBodyDesc,
                        latRequestBody,
                        lonRequestBody
                    )
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                _uploadResponse.value = ApiResult.ApiError(e.message())
            }
        }
    }
}