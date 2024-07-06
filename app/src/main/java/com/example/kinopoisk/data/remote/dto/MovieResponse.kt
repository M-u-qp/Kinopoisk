package com.example.kinopoisk.data.remote.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieResponse(
    val countries: List<Countries>,
    val description: String?,
    val filmLength: Int?,
    val genres: List<Genres>,
    val kinopoiskId: Int,
    val logoUrl: String?,
    val nameEn: String?,
    val nameRu: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingAgeLimits: String?,
    val ratingKinopoisk: Double?,
    val shortDescription: String?,
    val webUrl: String?,
    val year: Int?,
    val type: String?
): Parcelable

@Parcelize
data class Genres(
    val genre: String
): Parcelable

@Parcelize
data class Countries(
    val country: String
): Parcelable

