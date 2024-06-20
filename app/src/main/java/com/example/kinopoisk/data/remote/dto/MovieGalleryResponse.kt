package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.GalleryItem

data class MovieGalleryResponse(
    val items: List<GalleryItem>,
    val total: Int,
    val totalPages: Int
)