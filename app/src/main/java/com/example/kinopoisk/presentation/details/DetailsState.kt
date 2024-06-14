package com.example.kinopoisk.presentation.details

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Movie

data class DetailsState(
    val movie: Movie? = null,
    val loadingMovie: Boolean = false,
    val error: String? = null,
    val listMovie: List<Movie?> = emptyList(),
    val loadingCollections: Boolean = false,
    val listCollections: List<CollectionDB> = emptyList(),
    var showDialogForCollections: Boolean = false,
    var selectedCollection: String = ""
)