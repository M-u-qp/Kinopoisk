package com.example.kinopoisk.presentation.profile

import com.example.kinopoisk.domain.model.CollectionDB

data class ProfileState(
    val allCollections: List<CollectionDB> = emptyList(),
    val bookmarkCollection: CollectionDB? = null,
    val favoriteCollection: CollectionDB? = null,
)
