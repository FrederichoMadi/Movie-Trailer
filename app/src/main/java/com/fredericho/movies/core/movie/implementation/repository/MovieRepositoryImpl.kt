package com.fredericho.movies.core.movie.implementation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.fredericho.movies.core.movie.api.model.Credit
import com.fredericho.movies.core.movie.api.model.DetailMovie
import com.fredericho.movies.core.movie.api.model.VideoMovie
import com.fredericho.movies.core.movie.api.repository.MovieRepository
import com.fredericho.movies.core.movie.implementation.mapper.toCredit
import com.fredericho.movies.core.movie.implementation.mapper.toDetailMovie
import com.fredericho.movies.core.movie.implementation.mapper.toVideoMovie
import com.fredericho.movies.core.movie.implementation.paging.MoviePagingSource
import com.fredericho.movies.core.movie.implementation.remote.api.MovieApi
import com.fredericho.movies.util.ApiResult
import com.fredericho.movies.util.BaseResponse
import com.fredericho.movies.util.TOKEN
import com.fredericho.movies.util.result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {
    override fun getMoviePlayingNow() =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                MoviePagingSource(movieApi)
            }
        ).flow

    override suspend fun getDetailMovie(movieId: Int): BaseResponse<DetailMovie> =
        withContext(ioDispatcher) {
            when (val apiResult = movieApi.getDetailMovie(TOKEN, movieId).result()) {
                is ApiResult.Success -> {
                    val movies = apiResult.data.toDetailMovie()
                    BaseResponse.Success(movies)
                }

                is ApiResult.Error -> {
                    BaseResponse.Error("Failed to get data")
                }
            }
        }

    override suspend fun getVideoMovie(movieId: Int): BaseResponse<List<VideoMovie>> =
        withContext(ioDispatcher) {
            when (val apiResult = movieApi.getVideoMovie(TOKEN, movieId).result()) {
                is ApiResult.Success -> {
                    val videos = apiResult.data.results.map { it.toVideoMovie() }
                    BaseResponse.Success(videos)
                }

                is ApiResult.Error -> {
                    BaseResponse.Error("Failed to get data")
                }
            }
        }

    override suspend fun getCreditMovie(movieId: Int): BaseResponse<Credit> = withContext(ioDispatcher) {
        when(val apiResult = movieApi.getCreditMovie(TOKEN, movieId).result()) {
            is ApiResult.Success -> {
                val credit = apiResult.data.toCredit()
                BaseResponse.Success(credit)
            }
            is ApiResult.Error -> {
                BaseResponse.Error("Failed to get data")
            }
        }
    }
}