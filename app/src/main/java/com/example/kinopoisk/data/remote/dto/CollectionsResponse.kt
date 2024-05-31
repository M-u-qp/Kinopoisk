package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.Item

data class CollectionsResponse(
    val items: List<Item>,
    val total: Int,
    val totalPages: Int
)