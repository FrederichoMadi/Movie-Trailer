package com.fredericho.movies.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fredericho.movies.ui.features.detail.DetailScreen
import com.fredericho.movies.ui.features.favorite.FavoriteScreen
import com.fredericho.movies.ui.features.home.MovieScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieNavigation(
    navController: NavHostController = rememberNavController()
) {
    var bottomBarState by remember { mutableStateOf(true) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    bottomBarState = when (navBackStackEntry?.destination?.route) {
        MovieNavDestination.DETAIL.route -> false
        else -> true
    }

    Scaffold(bottomBar = {
        BottomNavigation(navController = navController, bottomBarState)
    }) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = MovieNavDestination.MOVIE.route
        ) {
            composable(MovieNavDestination.MOVIE.route) {
                LaunchedEffect(Unit) {
                    bottomBarState = true
                }

                MovieScreen(navigationDetail = { id ->
                    navController.navigate("${MovieNavDestination.DETAIL.route}/$id")
                })
            }
            composable(route = "${MovieNavDestination.DETAIL.route}/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })) {
                val id = it.arguments?.getInt("id", 0) ?: 0

                LaunchedEffect(Unit) {
                    bottomBarState = false
                }
                DetailScreen(id = id, navigateToBack = {
                    navController.navigateUp()
                })
            }
            composable(
                route = MovieNavDestination.FAVORITE.route
            ) {
                LaunchedEffect(Unit) {
                    bottomBarState = true
                }
                FavoriteScreen()
            }
        }
    }


}

@Composable
fun BottomNavigation(
    navController: NavHostController,
    bottomSheetState: Boolean,
) {
    val items = listOf(
        MovieNavDestination.MOVIE.route,
        MovieNavDestination.FAVORITE.route,
    )

    AnimatedVisibility(visible = bottomSheetState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        modifier = Modifier
    ) {
        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEachIndexed { i, item ->
                NavigationBarItem(
                    selected = currentRoute == item,
                    onClick = {
                        navController.navigate(item) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        if (i == 0) {
                            Icon(imageVector = Icons.Outlined.Home, contentDescription = null)
                        } else {
                            Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                        }
                    },
                    label = {
                        Text(text = item)
                    },
                )
            }
        }
    }
}