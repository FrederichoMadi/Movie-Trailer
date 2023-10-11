package com.fredericho.movies.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fredericho.movies.core.genres.api.model.Genre
import com.fredericho.movies.core.genres.api.repository.GenreRepository
import com.fredericho.movies.core.movie.api.model.Movie
import com.fredericho.movies.core.movie.api.repository.MovieRepository
import com.fredericho.movies.core.movie.implementation.database.entity.MovieEntity
import com.fredericho.movies.util.BaseResponse
import com.fredericho.movies.util.TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val genreRepository: GenreRepository,
) : ViewModel() {

    private val _movieState = MutableStateFlow(MovieUiState())
    val movieState = _movieState.asStateFlow()

    private val _genreState = MutableStateFlow(GenreUiState())
    val genreState = _genreState.asStateFlow()

    private val _movieLocalState = MutableStateFlow(MovieLocalState())
    val movieLocalState = _movieLocalState.asStateFlow()

    fun getMovies() = viewModelScope.launch {
        _movieState.update {
            it.copy(
                loading = true
            )
        }

        val movies = movieRepository.getMoviePopular().cachedIn(viewModelScope)

        _movieState.update {
            it.copy(
                flowMovies = movies,
                loading = false,
            )
        }

    }


    fun getGenre() = viewModelScope.launch {
        _genreState.update {
            it.copy(loading = true)
        }


        when (val result = genreRepository.getGenres(TOKEN)) {
            is BaseResponse.Success -> {
                _genreState.update {
                    it.copy(
                        genres = result.data,
                        loading = false,
                    )
                }
            }

            is BaseResponse.Error -> {
                _genreState.update {
                    it.copy(
                        messageError = result.message,
                        loading = false,
                    )
                }
            }

            else -> {
                _genreState.update {
                    it.copy(
                        loading = true,
                    )
                }
            }
        }

    }

    fun filteringMovies(genreId: Int, list: List<Movie>) {
        val filterMovies = mutableListOf<Movie>()
        viewModelScope.launch {
            list.forEach { movie ->
                movie.genreIds.forEach {
                    if (it == genreId) {
                        filterMovies.add(movie)
                    }
                }
            }
        }

        _movieState.update {
            it.copy(filterMovies = filterMovies)
        }
    }

    fun getLocalMovies() {
        _movieLocalState.update {
            it.copy(loading = true)
        }
        viewModelScope.launch {

            val movies = movieRepository.getMovies()

            if (movies.isEmpty()) {
                _movieLocalState.update {
                    it.copy(messageError = "Favorite movie is empty")
                }
            } else {
                _movieLocalState.update {
                    it.copy(movies = movies)
                }
            }
        }
    }

    fun insertMovie(movie: MovieEntity) {
        viewModelScope.launch {
            movieRepository.insertFavoriteMovie(movie = movie)
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            movieRepository.deleteFavoriteMovie(id)
        }
    }
}

data class MovieLocalState(
    val movies: List<MovieEntity> = emptyList(),
    val loading: Boolean = true,
    val messageError: String = "",
)

data class MovieUiState(
    val flowMovies: Flow<PagingData<Movie>> = emptyFlow(),
    val movies: List<Movie> = emptyList(),
    val filterMovies: List<Movie> = emptyList(),
    val loading: Boolean = true,
    val messageError: String = "",
)

data class GenreUiState(
    val genres: List<Genre> = emptyList(),
    val loading: Boolean = true,
    val messageError: String = "",
)