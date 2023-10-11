package com.fredericho.movies.ui.features.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.fredericho.movies.core.movie.implementation.mapper.toMovieEntity
import com.fredericho.movies.ui.features.home.components.MovieItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navigationDetail: (Int) -> Unit,
    context : Context = LocalContext.current
) {
    val movieState by viewModel.movieState.collectAsState()
    val genreState by viewModel.genreState.collectAsState()
    val movieLocalState by viewModel.movieLocalState.collectAsState()
    val movies = movieState.flowMovies.collectAsLazyPagingItems()
    var selectedFilter by remember { mutableIntStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.getMovies()
        viewModel.getLocalMovies()
    }

    LaunchedEffect(Unit) {
        viewModel.getGenre()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (!genreState.loading) {
                LazyRow {
                    items(
                        items = genreState.genres,
                        key = { genre -> genre.id }
                    ) { genre ->
                        ElevatedFilterChip(
                            selected = selectedFilter == genre.id,
                            onClick = {
                                if (selectedFilter == genre.id) {
                                    selectedFilter = 0
                                    viewModel.filteringMovies(
                                        selectedFilter,
                                        movies.itemSnapshotList.items
                                    )
                                } else {
                                    selectedFilter = genre.id
                                    viewModel.filteringMovies(
                                        selectedFilter,
                                        movies.itemSnapshotList.items
                                    )
                                }
                            },
                            label = { Text(text = genre.name) },
                            modifier = Modifier.padding(end = 4.dp),
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                selectedContainerColor = Color.Gray.copy(alpha = 0.4f),
                            ),
                        )
                    }
                    item {
                        ElevatedFilterChip(
                            selected = selectedFilter == 0,
                            onClick = {
                                selectedFilter = 0
                                viewModel.filteringMovies(
                                    selectedFilter,
                                    movies.itemSnapshotList.items
                                )
                            },
                            label = { Text(text = "Reset") },
                            modifier = Modifier.padding(end = 4.dp),
                            colors = FilterChipDefaults.elevatedFilterChipColors(
                                containerColor = Color.Gray.copy(alpha = 0.4f),
                            ),
                        )
                    }
                }
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                if (movieState.filterMovies.isEmpty() && selectedFilter == 0) {

                    items(
                        count = movies.itemCount,
                        key = movies.itemKey { it.id },
                    ) { index ->
                        val movie = movies[index]
                        val detailMovie = movieLocalState.movies.find { it.id == movie?.id }
                        MovieItem(
                            image = movie?.posterPath.toString(),
                            title = movie?.title.toString(),
                            isFavorite = detailMovie?.id == movie?.id,
                            insertFavoriteMovie = {
                                if (movie != null) {
                                    if(detailMovie?.id == movie.id) {
                                        viewModel.deleteMovie(movie.id)
                                        Toast.makeText(context, "Delete movie from Favorite", Toast.LENGTH_SHORT).show()

                                        viewModel.getLocalMovies()
                                    } else {
                                        viewModel.insertMovie(movie.toMovieEntity())
                                        Toast.makeText(context, "Add movie to Favorite", Toast.LENGTH_SHORT).show()

                                        viewModel.getLocalMovies()
                                    }
                                }
                            }
                        ) {
                            navigationDetail(movie?.id!!)
                        }
                    }


                    when (movies.loadState.refresh) {
                        is LoadState.Error -> {}
                        is LoadState.Loading -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.End,
                                ) {

                                    CircularProgressIndicator(color = Color.White)
                                }
                            }
                        }

                        else -> {}
                    }

                    when (movies.loadState.append) {
                        is LoadState.Error -> {}
                        is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                ) {

                                    CircularProgressIndicator(
                                        color = Color.White, modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                        else -> {}
                    }
                } else {
                    items(
                        items = movieState.filterMovies,
                        key = { movie -> movie.id }
                    ) { movie ->
                        val detailMovie = movieLocalState.movies.find { it.id == movie.id }
                        MovieItem(
                            image = movie.posterPath,
                            title = movie.originalTitle,
                            isFavorite = detailMovie?.id == movie.id,
                            navigationToDetail = { navigationDetail(movie.id) },
                            insertFavoriteMovie = {
                                if(detailMovie?.id == movie.id) {
                                    viewModel.deleteMovie(movie.id)
                                    Toast.makeText(context, "Delete movie from Favorite", Toast.LENGTH_SHORT).show()

                                    viewModel.getLocalMovies()
                                } else {
                                    viewModel.insertMovie(movie.toMovieEntity())
                                    Toast.makeText(context, "Add movie to Favorite", Toast.LENGTH_SHORT).show()

                                    viewModel.getLocalMovies()
                                }
                            },
                        )
                    }
                }
            }

        }
    }


}