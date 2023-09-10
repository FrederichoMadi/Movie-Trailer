package com.fredericho.movies.core.movie.api.repository

import androidx.paging.PagingData
import com.fredericho.movies.core.movie.api.model.Credit
import com.fredericho.movies.core.movie.api.model.DetailMovie
import com.fredericho.movies.core.movie.api.model.Movie
import com.fredericho.movies.core.movie.api.model.Review
import com.fredericho.movies.core.movie.api.model.VideoMovie
import com.fredericho.movies.util.BaseResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun getMoviePlayingNow(
    ) : Flow<PagingData<Movie>>

    suspend fun getDetailMovie(
        movieId : Int,
    ) : BaseResponse<DetailMovie>

    suspend fun getVideoMovie(
        movieId : Int,
    ) : BaseResponse<List<VideoMovie>>

    suspend fun getCreditMovie(
        movieId : Int,
    ) : BaseResponse<Credit>

    suspend fun getReviewMovie(
        movieId: Int
    ) : BaseResponse<List<Review>>

}