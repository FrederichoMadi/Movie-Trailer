package com.fredericho.movies.core.movie.implementation.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.fredericho.movies.core.movie.api.model.Movie
import com.fredericho.movies.core.movie.implementation.mapper.toMovie
import com.fredericho.movies.core.movie.implementation.remote.api.MovieApi
import com.fredericho.movies.util.TOKEN

class MoviePagingSource(
    private val movieApi: MovieApi,
) : PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = movieApi.getMovieNowPlaying(TOKEN, page)

            LoadResult.Page(
                data = response.body()?.results!!.map { it.toMovie() },
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.body()?.results?.isEmpty() == true) null else page.plus(1)
            )
        } catch (e : Exception) {
            LoadResult.Error(e)
        }
    }
}