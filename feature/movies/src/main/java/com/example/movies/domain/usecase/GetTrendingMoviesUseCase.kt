package com.example.movies.domain.usecase

import androidx.paging.PagingData
import com.example.movies.data.response.Result
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class GetTrendingMoviesUseCase (
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Result>> {
        return movieRepository.getTrendingMovies()
    }
}