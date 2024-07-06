package com.example.kinopoisk.presentation.home

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.CollectionItem
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val listCollections: List<CollectionDB> = emptyList(),
    val popularMovies: Flow<PagingData<CollectionItem>>? = null,
    val popularSerials: Flow<PagingData<CollectionItem>>? = null,
)