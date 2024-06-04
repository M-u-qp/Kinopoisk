package com.example.kinopoisk.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Movie(
    val countries: List<Country>,
    val description: String,
    val filmLength: Int,
    val genres: List<Genre>,
    @PrimaryKey val kinopoiskId: Int,
    val logoUrl: String,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingAgeLimits: String,
    val ratingKinopoisk: Double,
    val shortDescription: String,
    val webUrl: String,
    val year: Int
): Parcelable
