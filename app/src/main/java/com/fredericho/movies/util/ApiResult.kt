package com.fredericho.movies.util

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()

    data class Error(val messsage : String) : ApiResult<Nothing>()
}