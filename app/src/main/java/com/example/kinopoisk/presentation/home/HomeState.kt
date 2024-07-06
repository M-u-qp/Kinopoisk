package com.example.kinopoisk.presentation.home

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.domain.model.PremieresItem
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val listCollections: List<CollectionDB> = emptyList(),
    val popularMovies: Flow<PagingData<CollectionItem>>? = null,
    val popularSerials: Flow<PagingData<CollectionItem>>? = null,
    val premieres: List<PremieresItem> = emptyList(),
    val errorPremieres: String? = null,
)