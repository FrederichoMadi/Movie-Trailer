package com.fredericho.movies.core.movie.api.model

data class Credit(
    val cast : List<Cast> = emptyList(),
    val crew : List<Crew> = emptyList(),
)