package com.example.kinopoisk.presentation.home

import com.example.kinopoisk.domain.model.CollectionDB

data class HomeState(
    val listCollections: List<CollectionDB> = emptyList()
)