package com.fredericho.movies.ui.features.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fredericho.movies.core.movie.api.model.Credit
import com.fredericho.movies.core.movie.api.model.DetailMovie
import com.fredericho.movies.core.movie.api.model.VideoMovie
import com.fredericho.movies.core.movie.api.repository.MovieRepository
import com.fredericho.movies.util.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailMovieUiState())
    val detailState = _detailState.asStateFlow()

    private val _creditState = MutableStateFlow(CreditMovieUiState())
    val creditState = _creditState.asStateFlow()

    private val _videoState = MutableStateFlow(VideosMovieUiState())
    val videoState = _videoState.asStateFlow()

    fun getDetailMovie(movieId : Int) = viewModelScope.launch {
        _detailState.update {
            it.copy(loading = true)
        }

        when(val result = movieRepository.getDetailMovie(movieId)){
            is BaseResponse.Success -> {
                _detailState.update {
                    it.copy(
                        movie = result.data,
                        loading = false
                    )
                }
            }
            is BaseResponse.Error -> {
                _detailState.update {
                    it.copy(
                        messageError = result.message,
                        loading = false
                    )
                }
            }
            else -> {
                _detailState.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
    }

    fun getCreditMovie(movieId: Int) = viewModelScope.launch {
        _creditState.update {
            it.copy(loading = true)
        }

        when(val result = movieRepository.getCreditMovie(movieId)){
            is BaseResponse.Success -> {
                _creditState.update {
                    it.copy(
                        credit = result.data,
                        loading = false
                    )
                }
            }
            is BaseResponse.Error -> {
                _creditState.update {
                    it.copy(
                        messageError = result.message,
                        loading = false
                    )
                }
            }
            else -> {
                _creditState.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
    }

    fun getVideoMovie(movieId: Int) = viewModelScope.launch {
        _videoState.update {
            it.copy(loading = true)
        }

        when(val result = movieRepository.getVideoMovie(movieId)){
            is BaseResponse.Success -> {
                _videoState.update {
                    it.copy(
                        videos = result.data,
                        loading = false
                    )
                }
            }
            is BaseResponse.Error -> {
                _videoState.update {
                    it.copy(
                        messageError = result.message,
                        loading = false
                    )
                }
            }
            else -> {
                _videoState.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
    }
}

data class DetailMovieUiState(
    val movie : DetailMovie = DetailMovie(),
    val loading : Boolean = true,
    val messageError : String = "",
)

data class CreditMovieUiState(
    val credit : Credit = Credit(),
    val loading : Boolean = true,
    val messageError : String = "",
)

data class VideosMovieUiState(
    val videos : List<VideoMovie> = emptyList(),
    val loading : Boolean = true,
    val messageError : String = "",
)