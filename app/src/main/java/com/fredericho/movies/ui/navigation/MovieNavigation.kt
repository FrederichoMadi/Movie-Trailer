package com.fredericho.movies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fredericho.movies.ui.features.detail.DetailScreen
import com.fredericho.movies.ui.features.home.MovieScreen

@Composable
fun MovieNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = MovieNavDestination.MOVIE.route
    ) {
        composable(MovieNavDestination.MOVIE.route) {
            MovieScreen(
                navigationDetail = { id ->
                    navController.navigate("${MovieNavDestination.DETAIL.route}/$id")
                }
            )
        }
        composable(
            route = "${MovieNavDestination.DETAIL.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }
            )
        ) {
            val id = it.arguments?.getInt("id", 0) ?: 0

            DetailScreen(
                id = id,
                navigateToBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}