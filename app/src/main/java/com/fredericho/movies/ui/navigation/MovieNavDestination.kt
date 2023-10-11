package com.fredericho.movies.ui.navigation

enum class MovieNavDestination(
    val route : String,
) {
    MOVIE(route = "movies"),
    DETAIL(route = "detail"),
    FAVORITE(route = "favorite"),
}