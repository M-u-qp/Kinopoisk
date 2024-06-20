package com.example.kinopoisk.presentation.details.details_movie

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.domain.model.Staff

data class DetailsState(
    val movie: Movie? = null,
    val loadingMovie: Boolean = false,
    val errorMovies: String? = null,

    val listMovie: List<Movie?> = emptyList(),
    val loadingCollections: Boolean = false,
    val listCollections: List<CollectionDB> = emptyList(),
    var showDialogForCollections: Boolean = false,
    var selectedCollection: String = "",
    var movieViewed: Boolean = false,

    val listActors: List<Staff> = emptyList(),
    val listOtherStaff: List<Staff> = emptyList(),
    val errorStaff: String? = null,

    val similarMovies: List<SimilarItem> = emptyList(),
    val errorSimilar: String? = null,
)