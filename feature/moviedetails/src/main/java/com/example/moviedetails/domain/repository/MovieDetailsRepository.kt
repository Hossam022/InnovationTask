package com.example.moviedetails.domain.repository

import com.example.common.Response
import com.example.moviedetails.domain.model.MovieDetailsUiModel
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetailsById(movieId: Int): Flow<Response<MovieDetailsUiModel>>

}