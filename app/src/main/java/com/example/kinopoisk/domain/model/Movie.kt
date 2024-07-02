package com.example.kinopoisk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val id: Int,
    val collectionName: String,
    val countries: List<Country>,
    val description: String?,
    val filmLength: Int?,
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val logoUrl: String?,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingAgeLimits: String?,
    val ratingKinopoisk: Double?,
    val shortDescription: String?,
    val webUrl: String?,
    val year: Int?,
    val type: String?
): Parcelable
