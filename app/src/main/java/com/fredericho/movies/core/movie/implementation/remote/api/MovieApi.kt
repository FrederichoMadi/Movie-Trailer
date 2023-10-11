package com.fredericho.movies.core.movie.implementation.remote.api

import com.fredericho.movies.core.movie.implementation.remote.response.BaseMovieResponse
import com.fredericho.movies.core.movie.implementation.remote.response.BaseReviewResponse
import com.fredericho.movies.core.movie.implementation.remote.response.CreditResponse
import com.fredericho.movies.core.movie.implementation.remote.response.DetailMovieResponse
import com.fredericho.movies.core.movie.implementation.remote.response.MovieResponse
import com.fredericho.movies.core.movie.implementation.remote.response.VideoResponse
import com.fredericho.movies.util.TOKEN
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/popular")
    suspend fun getMoviePopular(
        @Header("Authorization") token : String,
        @Query("page") page : Int = 1,
    ) : Response<BaseMovieResponse<List<MovieResponse>>>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Header("Authorization") token : String,
        @Path("movie_id") movieId : Int,
    ) : Response<DetailMovieResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getVideoMovie(
        @Header("Authorization") token : String,
        @Path("movie_id") movieId : Int,
    ) : Response<VideoResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditMovie(
        @Header("Authorization") token : String,
        @Path("movie_id") movieId : Int,
    ) : Response<CreditResponse>

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviewMovie(
        @Header("Authorization") token : String = TOKEN,
        @Path("movie_id") movieId : Int,
        @Query("page") page : Int = 1,
    ) : Response<BaseReviewResponse>
}