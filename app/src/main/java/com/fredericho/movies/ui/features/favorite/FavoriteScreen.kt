package com.fredericho.movies.ui.features.favorite

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.fredericho.movies.ui.features.home.MovieViewModel
import com.fredericho.movies.ui.features.home.components.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    context: Context = LocalContext.current,
) {
    val movieLocalState by viewModel.movieLocalState.collectAsState()
    val movies = movieLocalState.movies

    LaunchedEffect(key1 = Unit) {
        viewModel.getLocalMovies()
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (movies.isEmpty()) {
                Text(
                    text = movieLocalState.messageError, modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        count = movies.size,
                        key = { movies[it].id },
                    ) { index ->
                        val movie = movies[index]
                        val detailMovie = movieLocalState.movies.find { it.id == movie.id }
                        MovieItem(image = movie.posterPath,
                            title = movie.title,
                            isFavorite = detailMovie?.id == movie.id,
                            insertFavoriteMovie = {
                                if (detailMovie?.id == movie.id) {
                                    viewModel.deleteMovie(movie.id)
                                    Toast.makeText(
                                        context, "Delete movie from Favorite", Toast.LENGTH_SHORT
                                    ).show()

                                    viewModel.getLocalMovies()
                                }

                            }) {}
                    }
                }
            }

        }
    }
}