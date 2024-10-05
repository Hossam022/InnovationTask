package com.example.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.example.movies.data.response.Result

interface MovieRepository {
    fun getTrendingMovies(): Flow<PagingData<Result>>

}