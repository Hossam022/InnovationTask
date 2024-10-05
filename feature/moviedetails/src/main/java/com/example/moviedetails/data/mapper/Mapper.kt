package com.example.moviedetails.data.mapper

import com.example.moviedetails.data.response.MovieDetail
import com.example.moviedetails.domain.model.MovieDetailsUiModel

fun MovieDetail.toMovieDetailUi(): MovieDetailsUiModel {
    return MovieDetailsUiModel(
        backDropPath = backDropPath,
        id = id,
        originalLanguage = originalLanguage,
        overview = overview,
        releaseDate = releaseDate,
        title = title,
        status = status,
        genres = genres
    )
}