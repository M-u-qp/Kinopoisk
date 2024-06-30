package com.example.kinopoisk.presentation.search_filter

import com.example.kinopoisk.domain.model.FilterItem

sealed class SearchFilterEvent {
    data object SearchFilterMovies : SearchFilterEvent()
}