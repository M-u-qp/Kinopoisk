package com.example.kinopoisk.presentation.details.details_movie

import com.example.kinopoisk.domain.model.Movie

sealed class DetailsEvent {

    data class ReadyToViewMovie(val movie: Movie): DetailsEvent()

    data object RemoveSideEffect : DetailsEvent()

    data class FavoriteMovie(val movie: Movie) : DetailsEvent()

    data class AddMovieInCollection(val movie: Movie) : DetailsEvent()

    data class AutoAddMovieInViewed(val movie: Movie) : DetailsEvent()
}