package com.example.moviedetails.presentation

sealed interface MovieDetailsEvents {
    data class GetMovieDetails(val movieID: Int) : MovieDetailsEvents

}