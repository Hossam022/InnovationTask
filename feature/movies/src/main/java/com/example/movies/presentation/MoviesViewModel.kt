package com.example.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movies.data.mapper.toMovieUiModel
import com.example.movies.domain.model.MovieUIModel
import com.example.movies.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCases: GetTrendingMoviesUseCase
) : ViewModel(){

    private val _state = MutableStateFlow<PagingData<MovieUIModel>>(PagingData.empty())
    val state: StateFlow<PagingData<MovieUIModel>> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = PagingData.empty(),
    )

    init {
        onEvent(MoviesUiEvents.GetTrendingMovies)
    }

    private fun onEvent(event: MoviesUiEvents) {
        when (event) {
            MoviesUiEvents.GetTrendingMovies -> {
                getTrendingMovies()
            }
        }

    }

    private fun getTrendingMovies() {
        moviesUseCases().cachedIn(viewModelScope).onEach {
            _state.value = it.map { movie -> movie.toMovieUiModel() }
        }.launchIn(viewModelScope)
    }

}