package com.example.moviedetails.di

import com.example.common.di.DefaultDispatcher
import com.example.moviedetails.data.remote.MoviesDetailsService
import com.example.moviedetails.data.repository.MovieDetailsRepositoryImpl
import com.example.moviedetails.domain.repository.MovieDetailsRepository
import com.example.moviedetails.domain.usecase.GetMovieDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {

    @Singleton
    @Provides
    fun provideMoviesDetailsService(retrofit: Retrofit): MoviesDetailsService =
        retrofit.create(MoviesDetailsService::class.java)


    @Provides
    @Singleton
    fun provideMovieDetailsRepository(
        moviesDetailsService: MoviesDetailsService,
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(
            detailApiService = moviesDetailsService
        )
    }

    @Provides
    @Singleton
    fun provideMovieDetailsUseCases(
        movieRepository: MovieDetailsRepository,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): GetMovieDetailsUseCase {
        return GetMovieDetailsUseCase(
            movieDetailsRepository = movieRepository, defaultDispatcher = defaultDispatcher
        )
    }
}