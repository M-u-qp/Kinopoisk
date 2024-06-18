package com.example.kinopoisk.presentation.collectiondb

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionDBViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases
): ViewModel() {

    private val _state = mutableStateOf(CollectionDBState())
    val state: State<CollectionDBState> = _state

    suspend fun getMoviesCollection(nameCollection: String) {
        collectionsUseCases.getCollectionInDB(nameCollection).collect{ listMovies ->
            _state.value = _state.value.copy(moviesCollection = listMovies)
        }
    }
}