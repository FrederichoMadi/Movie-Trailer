package com.fredericho.movies.util

import retrofit2.Response

fun <T : Any> Response<T>.result(): ApiResult<T> {
    return try {
        val body = body()

        if (isSuccessful && body != null) {
            ApiResult.Success(data = body)
        } else {
            ApiResult.Error(messsage = errorBody().toString())
        }
    } catch (t: Throwable) {
        ApiResult.Error(messsage = errorBody().toString())
    }
}