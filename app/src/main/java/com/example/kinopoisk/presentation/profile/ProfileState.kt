package com.example.kinopoisk.presentation.profile

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Movie

data class ProfileState(
    val allCollections: List<CollectionDB> = emptyList(),
    val anyCollection: List<Movie?> = emptyList(),
    val sizeAnyCollection: Map<String, List<Movie?>> = mutableMapOf(),
    var showDialogForCreateCollection: Boolean = false
)
