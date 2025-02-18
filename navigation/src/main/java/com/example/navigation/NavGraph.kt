package com.example.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.common.utils.Screen
import com.example.moviedetails.presentation.navigation.movieDetailsScreen
import com.example.movies.presentation.navigation.moviesScreen

private const val TRANSITION_DURATION = 400

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.MoviesScreen.route,
        enterTransition = {
            fadeIn(
                animationSpec = tween(TRANSITION_DURATION)
            )
        }, exitTransition = {
            fadeOut(
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
    ) {
        moviesScreen(navController)
        movieDetailsScreen(navController)
    }
}

