package com.example.kinopoisk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchFilm(
    val countries: List<Country>,
    val description: String?,
    val filmId: Int,
    val filmLength: String?,
    val genres: List<Genre>,
    val nameEn: String?,
    val nameRu: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val rating: String?,
    val ratingVoteCount: Int?,
    val type: String?,
    val year: String?
): Parcelable