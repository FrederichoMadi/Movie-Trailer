package com.fredericho.movies.core.movie.api.model

data class VideoMovie(
    val site: String,
    val size: Int,
    val iso31661: String,
    val name: String,
    val official: Boolean,
    val id: String,
    val type: String,
    val publishedAt: String,
    val iso6391: String,
    val key: String
)