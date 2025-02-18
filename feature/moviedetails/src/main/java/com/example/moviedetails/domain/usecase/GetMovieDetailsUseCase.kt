package com.example.moviedetails.domain.usecase

import com.example.common.Response
import com.example.moviedetails.domain.model.MovieDetailsUiModel
import com.example.moviedetails.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetMovieDetailsUseCase (
    private val movieDetailsRepository: MovieDetailsRepository,
    private val defaultDispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(movieId: Int): Flow<Response<MovieDetailsUiModel>> {
        return movieDetailsRepository.getMovieDetailsById(movieId).flowOn(defaultDispatcher)
    }
}