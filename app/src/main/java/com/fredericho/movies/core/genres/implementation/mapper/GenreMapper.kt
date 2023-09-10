package com.fredericho.movies.core.genres.implementation.mapper

import com.fredericho.movies.core.genres.api.model.Genre
import com.fredericho.movies.core.genres.implementation.remote.response.GenreResponse

internal fun GenreResponse.toGenre() : Genre =
    Genre(
        id = id,
        name = name,
    )

internal fun Genre.toGenreResponse() : GenreResponse =
    GenreResponse(
        id = id,
        name = name,
    )