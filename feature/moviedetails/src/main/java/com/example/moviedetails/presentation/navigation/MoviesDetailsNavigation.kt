package com.example.moviedetails.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.common.utils.Constants.MOVIE_ID
import com.example.common.utils.Screen
import com.example.moviedetails.presentation.MovieDetailsScreen

fun NavGraphBuilder.movieDetailsScreen(navController: NavController) {
    composable(
        route = Screen.DetailScreen.route + "/{${MOVIE_ID}}",
        arguments = listOf(navArgument(MOVIE_ID) { type = NavType.IntType })
    ) {
        MovieDetailsScreen(it.arguments?.getInt(MOVIE_ID) ?: -1,
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}