package com.fredericho.movies.ui.navigation

enum class MovieNavDestination(
    val route : String,
    val args : String? = null,
) {
    MOVIE(route = "movies"),
    DETAIL(route = "detail")
}