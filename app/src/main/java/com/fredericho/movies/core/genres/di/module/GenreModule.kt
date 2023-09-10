package com.fredericho.movies.core.genres.di.module

import com.fredericho.movies.core.genres.api.repository.GenreRepository
import com.fredericho.movies.core.genres.implementation.remote.api.GenreApi
import com.fredericho.movies.core.genres.implementation.repository.GenreRepositoryImpl
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
object GenreModule {

    @Provides
    @Singleton
    fun provideGenreApi(retrofit: Retrofit) : GenreApi =
        retrofit.create(GenreApi::class.java)

    @Provides
    fun provideGenreRepository(
        genreApi: GenreApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): GenreRepository {
        return GenreRepositoryImpl(
            ioDispatcher,
            genreApi,
        )
    }
}