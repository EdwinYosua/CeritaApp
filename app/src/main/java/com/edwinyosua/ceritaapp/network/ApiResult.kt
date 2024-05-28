package com.edwinyosua.ceritaapp.network

sealed class ApiResult<out R> private constructor() {
    data class ApiSuccess<out T>(val data: T) : ApiResult<T>()
    data class ApiError(val error: String) : ApiResult<Nothing>()
    object Loading : ApiResult<Nothing>()
}