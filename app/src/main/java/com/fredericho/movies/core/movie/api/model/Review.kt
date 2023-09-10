package com.fredericho.movies.core.movie.api.model

data class Review(
    val authorDetails: Author? = null,
    val updatedAt: String? = null,
    val author: String? = null,
    val createdAt: String? = null,
    val id: String? = null,
    val content: String? = null,
    val url: String? = null
)