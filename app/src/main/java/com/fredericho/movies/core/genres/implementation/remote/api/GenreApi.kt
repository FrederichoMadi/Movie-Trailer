package com.fredericho.movies.core.genres.implementation.remote.api

import com.fredericho.movies.core.genres.implementation.remote.response.BaseGenreResponse
import com.fredericho.movies.core.genres.implementation.remote.response.GenreResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface GenreApi {
    @GET("genre/movie/list")
    suspend fun getGenre(
        @Header("Authorization") token: String,
    ) : Response<BaseGenreResponse<GenreResponse>>
}