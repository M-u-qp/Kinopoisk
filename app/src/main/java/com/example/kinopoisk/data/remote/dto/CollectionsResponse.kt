package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.CollectionItem

data class CollectionsResponse(
    val items: List<CollectionItem>,
    val total: Int,
    val totalPages: Int
)