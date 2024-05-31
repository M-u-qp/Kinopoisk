package com.example.kinopoisk.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(keyword = event.keyword)
            }

            is SearchEvent.SearchMovies -> {
                searchMovies()
            }
        }
    }

    private fun searchMovies() {
        val movies = moviesUseCases.searchMovies(
            keyword = state.value.keyword
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(movies = movies)
    }
}