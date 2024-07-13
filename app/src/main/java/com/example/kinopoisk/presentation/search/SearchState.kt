package com.example.kinopoisk.presentation.search

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.common.SortSearchFilter
import com.example.kinopoisk.presentation.common.TypeSearchFilter
import com.example.kinopoisk.presentation.common.listLast100Years
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val keyword: String? = null,
    val ratingFrom: Int = 0,
    val ratingTo: Int = 10,
    val selectedCountry: List<Int> = emptyList(),
    val selectedGenre: List<Int> = emptyList(),
    val yearsFrom: Int = listLast100Years().last(),
    val yearsTo: Int = listLast100Years().first(),
    val typeSearchFilter: String = TypeSearchFilter.ALL.name,
    val sortSearchFilter: String = SortSearchFilter.YEAR.name,
    val viewedMovies: Boolean = false,

    val resetFilter: Boolean = false,

    val filterMovies: Flow<PagingData<FilterItem>>? = null,

    val moviesCollection: List<Movie?> = emptyList()
)