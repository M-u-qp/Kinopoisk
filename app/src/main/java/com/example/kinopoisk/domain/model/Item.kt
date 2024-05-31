package com.example.kinopoisk.domain.model

data class Item(
    val countries: List<Country?> = emptyList(),
    val genres: List<Genre?> = emptyList(),
    val kinopoiskId: Int = 0,
    val nameEn: String? = null,
    val nameOriginal: String? = null,
    val nameRu: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val ratingImbd: Double? = null,
    val ratingKinopoisk: Double? = null,
    val type: String = "",
    val year: String = ""
)