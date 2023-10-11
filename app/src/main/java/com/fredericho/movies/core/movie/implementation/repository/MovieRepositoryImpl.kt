package com.fredericho.movies.core.movie.implementation.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fredericho.movies.core.movie.api.model.Credit
import com.fredericho.movies.core.movie.api.model.DetailMovie
import com.fredericho.movies.core.movie.api.model.Movie
import com.fredericho.movies.core.movie.api.model.Review
import com.fredericho.movies.core.movie.api.model.VideoMovie
import com.fredericho.movies.core.movie.api.repository.MovieRepository
import com.fredericho.movies.core.movie.implementation.database.dao.MovieDao
import com.fredericho.movies.core.movie.implementation.database.entity.MovieEntity
import com.fredericho.movies.core.movie.implementation.mapper.toCredit
import com.fredericho.movies.core.movie.implementation.mapper.toDetailMovie
import com.fredericho.movies.core.movie.implementation.mapper.toReview
import com.fredericho.movies.core.movie.implementation.mapper.toVideoMovie
import com.fredericho.movies.core.movie.implementation.paging.MoviePagingSource
import com.fredericho.movies.core.movie.implementation.remote.api.MovieApi
import com.fredericho.movies.util.ApiResult
import com.fredericho.movies.util.BaseResponse
import com.fredericho.movies.util.TOKEN
import com.fredericho.movies.util.result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val movieDao: MovieDao,
    private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {
    override fun getMoviePopular(): Flow<PagingData<Movie>> =
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

    override suspend fun getReviewMovie(movieId: Int): BaseResponse<List<Review>> = withContext(ioDispatcher) {
        when(val apiResult = movieApi.getReviewMovie(movieId = movieId).result()) {
            is ApiResult.Success -> {
                val review = apiResult.data.results.map { it.toReview() }
                BaseResponse.Success(review)
            }
            is ApiResult.Error -> {
                BaseResponse.Error("Failed to get data")
            }
        }
    }

    override suspend fun getMovies(): List<MovieEntity> = withContext(ioDispatcher) {
        movieDao.getMovies()
    }

    override suspend fun insertFavoriteMovie(movie : MovieEntity) = withContext(ioDispatcher) {
        movieDao.insertFavoriteMovie(movie)
    }

    override suspend fun deleteFavoriteMovie(id: Int) = withContext(ioDispatcher) {
        movieDao.removeFavorite(id)
    }
}