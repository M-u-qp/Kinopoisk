package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.SimilarItem

data class SimilarMoviesResponse(
    val items: List<SimilarItem>,
    val total: Int
)