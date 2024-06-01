package com.example.kinopoisk.presentation.details

sealed class DetailsEvent {

    data object SaveMovie: DetailsEvent()

    data object LikeMovie: DetailsEvent()
}