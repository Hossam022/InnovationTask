package com.example.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.filter
import com.example.common.utils.Constants
import com.example.movies.data.dataSource.MovieDataSource
import com.example.movies.data.response.Result
import com.example.movies.data.remote.MoviesService
import com.example.movies.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl (
    private val apiService: MoviesService,
    private val defaultDispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getTrendingMovies(): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
            ),
            pagingSourceFactory = {
                MovieDataSource(
                    apiService = apiService,
                    defaultDispatcher = defaultDispatcher
                )
            },
        ).flow.map {
            //remove duplicate entries from api response
            val movieMap = mutableSetOf<Int>()
            it.filter { movie ->
                if (movieMap.contains(movie.id)) {
                    false
                } else {
                    movieMap.add(movie.id)
                }
            }
        }
    }
}