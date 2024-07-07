package com.example.kinopoisk.presentation.search

import android.util.Log
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

            is SearchEvent.UpdateFilterQueryAndSearch -> {
                searchFilterMovies(
                    ratingFrom = event.ratingFrom,
                    ratingTo = event.ratingTo,
                    selectedCountry = event.selectedCountry,
                    selectedGenre = event.selectedGenre,
                    yearsFrom = event.yearsFrom,
                    yearsTo = event.yearsTo,
                    typeSearchFilter = event.typeSearchFilter,
                    sortSearchFilter = event.sortSearchFilter
                )
            }
        }
    }

    private fun searchFilterMovies(
        ratingFrom: Int,
        ratingTo: Int,
        selectedCountry: List<Int>,
        selectedGenre: List<Int>,
        yearsFrom: Int,
        yearsTo: Int,
        typeSearchFilter: String,
        sortSearchFilter: String,
    ) {
        val filterMovies = moviesUseCases.searchFilterMovies(
            countries = selectedCountry,
            genres = selectedGenre,
            order = sortSearchFilter,
            type = typeSearchFilter,
            ratingFrom = ratingFrom,
            ratingTo = ratingTo,
            yearFrom = yearsFrom,
            yearTo = yearsTo
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(filterMovies = filterMovies)
        Log.d("TAG", "VM - $filterMovies")
    }

    private fun searchMovies() {
        val movies = moviesUseCases.searchMovies(
            keyword = state.value.keyword
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(movies = movies)
    }

    private suspend fun getMoviesCollection(nameCollection: String) {
        collectionsUseCases.getCollectionInDB(nameCollection).collect{ listMovies ->
            _state.value = _state.value.copy(moviesCollection = listMovies)
        }
    }
}