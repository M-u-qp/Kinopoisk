package com.example.kinopoisk.domain.model

data class SimilarItem(
    val filmId: Int?,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val relationType: String?
)