package com.example.movies.presentation

sealed interface MoviesUiEvents {
    data object GetTrendingMovies : MoviesUiEvents
}