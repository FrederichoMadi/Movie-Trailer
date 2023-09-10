package com.fredericho.movies.core.genres.api.repository

import com.fredericho.movies.core.genres.api.model.Genre
import com.fredericho.movies.util.BaseResponse

interface GenreRepository {
    suspend fun getGenres(
        token : String,
    ) : BaseResponse<List<Genre>>
}