package com.example.kinopoisk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kinopoisk.domain.model.Country
import com.example.kinopoisk.domain.model.Genre


@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var collectionName: String,
    val countries: List<Country>,
    val description: String,
    val filmLength: Int,
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val logoUrl: String,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingAgeLimits: String,
    val ratingKinopoisk: Double,
    val shortDescription: String,
    val webUrl: String,
    val year: Int,
    val type: String
)
