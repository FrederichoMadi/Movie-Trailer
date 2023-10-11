package com.fredericho.movies.core.movie.di.module

import com.fredericho.movies.core.movie.api.repository.MovieRepository
import com.fredericho.movies.core.movie.implementation.database.dao.MovieDao
import com.fredericho.movies.core.movie.implementation.remote.api.MovieApi
import com.fredericho.movies.core.movie.implementation.repository.MovieRepositoryImpl
import com.fredericho.movies.util.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit) : MovieApi =
        retrofit.create(MovieApi::class.java)

    @Provides
    fun provideMovieRepository(
        movieApi: MovieApi,
        movieDao: MovieDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): MovieRepository {
        return MovieRepositoryImpl(
            movieApi,
            movieDao,
            ioDispatcher,
        )
    }
}