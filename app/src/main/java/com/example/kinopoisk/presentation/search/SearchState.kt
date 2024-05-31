package com.example.kinopoisk.presentation.search

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.Film
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val keyword: String = "",
    val movies: Flow<PagingData<Film>>? = null
)