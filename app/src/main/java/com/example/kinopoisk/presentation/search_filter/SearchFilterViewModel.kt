package com.example.kinopoisk.presentation.search_filter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
): ViewModel() {

    private val _state = mutableStateOf(SearchFilterState())
    val state: MutableState<SearchFilterState> = _state

    fun updateRatingSlider(ratingPosition: ClosedFloatingPointRange<Float>) {
        _state.value = _state.value.copy(ratingPosition = ratingPosition)
    }
}