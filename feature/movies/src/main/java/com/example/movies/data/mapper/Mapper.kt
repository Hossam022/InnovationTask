package com.example.movies.data.mapper

import com.example.movies.data.response.Result
import com.example.movies.domain.model.MovieUIModel

fun Result.toMovieUiModel(): MovieUIModel {
    return MovieUIModel(
        id = id,
        title = title,
        image = posterPath,
        year = releaseDate
    )
}