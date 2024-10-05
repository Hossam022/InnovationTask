package com.example.movies.di

import com.example.common.di.DefaultDispatcher
import com.example.movies.data.remote.MoviesService
import com.example.movies.data.repository.MovieRepositoryImpl
import com.example.movies.domain.repository.MovieRepository
import com.example.movies.domain.usecase.GetTrendingMoviesUseCase
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
    fun provideApiService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)


    @Provides
    @Singleton
    fun provideMovieRepository(
        moviesService: MoviesService,
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(
            apiService = moviesService,
            defaultDispatcher = defaultDispatcher
        )
    }


    @Provides
    @Singleton
    fun provideMoviesUseCases(
        movieRepository: MovieRepository,
    ): GetTrendingMoviesUseCase {
        return GetTrendingMoviesUseCase(
            movieRepository = movieRepository
        )
    }

}