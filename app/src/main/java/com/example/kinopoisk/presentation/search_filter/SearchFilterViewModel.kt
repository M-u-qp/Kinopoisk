package com.example.kinopoisk.presentation.search_filter

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.CountryFilter
import com.example.kinopoisk.domain.model.GenreFilter
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.utils.Resource
import com.example.kinopoisk.presentation.common.SortSearchFilter
import com.example.kinopoisk.presentation.common.TypeSearchFilter
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
        _state.value = _state.value.copy(
            ratingPosition = ratingPosition,
        )
    }

    fun updateVisibleDialogCountriesOrGenres(show: Boolean) {
        _state.value = _state.value.copy(showDialogCountriesOrGenres = show)
    }

    fun updateSelectedCountryOrGenre(selected: String) {
        _state.value = _state.value.copy(selectedCountryOrGenre = selected)
    }

    fun updateSelectedCountry(countryFilter: CountryFilter) {
        _state.value = _state.value.copy(
            selectedCountry = countryFilter,
        )
    }

    fun updateSelectedGenre(genreFilter: GenreFilter) {
        _state.value = _state.value.copy(
            selectedGenre = genreFilter,
        )
    }

    fun updateVisibleDialogDatePicker(show: Boolean) {
        _state.value = _state.value.copy(showDialogDatePicker = show)
    }

    fun updateYearsPosition(yearsPosition: IntRange) {
        _state.value = _state.value.copy(
            yearsPosition = yearsPosition,
        )
    }

    fun updateType(typeSearchFilter: TypeSearchFilter) {
        _state.value = _state.value.copy(
            typeSearchFilter = typeSearchFilter,
        )
    }

    fun updateSort(sortSearchFilter: SortSearchFilter) {
        _state.value = _state.value.copy(
            sortSearchFilter = sortSearchFilter,
        )
    }

    fun updateViewed(viewed: Boolean) {
        _state.value = _state.value.copy(
            viewedMovies = viewed,
        )
    }

    fun updateFilterData(): FilterData {
        return FilterData(
            ratingFrom = state.value.ratingPosition.start.toInt(),
            ratingTo = state.value.ratingPosition.endInclusive.toInt(),
            selectedCountry = listOf(state.value.selectedCountry.id),
            selectedGenre = listOf(state.value.selectedGenre.id),
            yearsFrom = state.value.yearsPosition.last,
            yearsTo = state.value.yearsPosition.first,
            typeSearchFilter = state.value.typeSearchFilter.name,
            sortSearchFilter = state.value.sortSearchFilter.name,
            viewedMovies = state.value.viewedMovies
        )
    }

    private fun getCountriesAndGenres() {
        if (state.value.listCountriesAndGenres == null) {
            viewModelScope.launch {
                when (val countriesAndGenres = moviesUseCases.getCountriesAndGenres()) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            listCountriesAndGenres = countriesAndGenres.data,
                            errorListCountriesAndGenres = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            errorListCountriesAndGenres = countriesAndGenres.exception.toString()
                        )
                    }

                    else -> Unit
                }
            }
        }
    }
}