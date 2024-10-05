package com.example.moviedetails.data.repository

import com.example.common.Response
import com.example.moviedetails.data.mapper.toMovieDetailUi
import com.example.moviedetails.data.remote.MoviesDetailsService
import com.example.moviedetails.domain.repository.MovieDetailsRepository
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class MovieDetailsRepositoryImpl (
    private val detailApiService: MoviesDetailsService,
) : MovieDetailsRepository {
    override suspend fun getMovieDetailsById(movieId: Int) = flow {
        return@flow try {
            emit(Response.Loading)
            val result = detailApiService.getMovieDetails(movieId = movieId).toMovieDetailUi()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e))
        } catch (e: HttpException) {
            emit(Response.Error(e))
        }

    }
}