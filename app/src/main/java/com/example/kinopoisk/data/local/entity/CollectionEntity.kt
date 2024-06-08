package com.example.kinopoisk.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CollectionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val nameCollection: String,
    val moviesList: List<MovieEntity>
)