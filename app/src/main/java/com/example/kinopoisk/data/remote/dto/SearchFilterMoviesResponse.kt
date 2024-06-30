package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.FilterItem

data class SearchFilterMoviesResponse(
    val items: List<FilterItem>,
    val total: Int,
    val totalPages: Int
)