package com.example.kinopoisk.presentation.search

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.SearchFilm
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val keyword: String = "",
    val movies: Flow<PagingData<SearchFilm>>? = null,
    val filterMovies: Flow<PagingData<FilterItem>>? = null
)