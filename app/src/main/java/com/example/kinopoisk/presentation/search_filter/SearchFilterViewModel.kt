package com.example.kinopoisk.presentation.search_filter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.CountryFilter
import com.example.kinopoisk.domain.model.GenreFilter
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchFilterViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchFilterState())
    val state: MutableState<SearchFilterState> = _state

    init {
        getCountriesAndGenres()
    }

    fun updateRatingSlider(ratingPosition: ClosedFloatingPointRange<Float>) {
        _state.value = _state.value.copy(ratingPosition = ratingPosition)
    }

    fun updateVisibleDialogCountriesOrGenres(show: Boolean) {
        _state.value = _state.value.copy(showDialogCountriesOrGenres = show)
    }

    fun updateSelectedCountryOrGenre(selected: String) {
        _state.value = _state.value.copy(selectedCountryOrGenre = selected)
    }

    fun updateSelectedCountry(countryFilter: CountryFilter) {
        _state.value = _state.value.copy(selectedCountry = countryFilter)
    }

    fun updateSelectedGenre(genreFilter: GenreFilter) {
        _state.value = _state.value.copy(selectedGenre = genreFilter)
    }

    fun updateVisibleDialogDatePicker(show: Boolean) {
        _state.value = _state.value.copy(showDialogDatePicker = show)
    }

    fun updateYearsPosition(yearsPosition: IntRange) {
        _state.value = _state.value.copy(yearsPosition = yearsPosition)
    }

    private fun getCountriesAndGenres() {
        if (state.value.listCountriesAndGenres == null) {
            viewModelScope.launch {
                val listCountriesAndGenres = moviesUseCases.getCountriesAndGenres()
                if (listCountriesAndGenres.data != null) {
                    _state.value =
                        _state.value.copy(listCountriesAndGenres = listCountriesAndGenres.data)
                }
            }
        }
    }
}