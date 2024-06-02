package com.example.kinopoisk.presentation.details

import com.example.kinopoisk.domain.model.Movie

sealed class DetailsEvent {

    data class UpsertDeleteMovie(val movie: Movie): DetailsEvent()

    data object RemoveSideEffect : DetailsEvent()

    data object LikeMovie: DetailsEvent()
}