package com.fredericho.movies.core.genres.implementation.repository

import com.fredericho.movies.core.genres.api.model.Genre
import com.fredericho.movies.core.genres.api.repository.GenreRepository
import com.fredericho.movies.core.genres.implementation.mapper.toGenre
import com.fredericho.movies.core.genres.implementation.remote.api.GenreApi
import com.fredericho.movies.util.ApiResult
import com.fredericho.movies.util.BaseResponse
import com.fredericho.movies.util.result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val ioDispatcher :CoroutineDispatcher,
    private val genreApi : GenreApi,
) : GenreRepository{
    override suspend fun getGenres(token: String): BaseResponse<List<Genre>>
    = withContext(ioDispatcher) {
        val apiResult = genreApi.getGenre(
            token = token
        ).result()

        when(apiResult) {
            is ApiResult.Success -> {
                val result = apiResult.data.genres.map { it.toGenre() }
                BaseResponse.Success(result)
            }
            is ApiResult.Error -> {
                BaseResponse.Error("Failed get data from api")
            }
        }
    }
}