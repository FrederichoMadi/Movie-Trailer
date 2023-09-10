package com.fredericho.movies.util

sealed interface BaseResponse<out T> {
    object Loading : BaseResponse<Nothing>
    open class Error(open val message: String) : BaseResponse<Nothing>
    data class Success<T>(val data: T) : BaseResponse<T>
}