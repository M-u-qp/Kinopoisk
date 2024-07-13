package com.example.kinopoisk.presentation.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
    private val collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    init {
        viewModelScope.launch {
            getMoviesCollection(TitleCollectionsDB.VIEWED.value)
        }
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = _state.value.copy(keyword = event.keyword)
            }

            is SearchEvent.SearchMovies -> {
                searchMovies()
            }

            is SearchEvent.UpdateFilterQuery -> {
                _state.value = _state.value.copy(
                    ratingFrom = event.ratingFrom,
                    ratingTo = event.ratingTo,
                    selectedCountry = event.selectedCountry,
                    selectedGenre = event.selectedGenre,
                    yearsFrom = event.yearsFrom,
                    yearsTo = event.yearsTo,
                    typeSearchFilter = event.typeSearchFilter,
                    sortSearchFilter = event.sortSearchFilter,
                    keyword = event.keyword,
                    viewedMovies = event.viewedMovies
                )
            }
        }
    }

    private fun searchMovies() {
        val filterMovies = moviesUseCases.searchFilterMovies(
            countries = state.value.selectedCountry,
            genres = state.value.selectedGenre,
            order = state.value.sortSearchFilter,
            type = state.value.typeSearchFilter,
            ratingFrom = state.value.ratingFrom,
            ratingTo = state.value.ratingTo,
            yearFrom = state.value.yearsFrom,
            yearTo = state.value.yearsTo,
            keyword = state.value.keyword ?: "keyword"
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(filterMovies = filterMovies)
    }

    private suspend fun getMoviesCollection(nameCollection: String) {
        collectionsUseCases.getCollectionInDB(nameCollection).collect{ listMovies ->
            _state.value = _state.value.copy(moviesCollection = listMovies)
        }
    }

    fun updateResetFilter(reset: Boolean) {
        _state.value = _state.value.copy(resetFilter = reset)
    }
}