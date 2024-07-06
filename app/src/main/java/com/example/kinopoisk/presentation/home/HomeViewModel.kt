package com.example.kinopoisk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.presentation.common.TitleCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            getAllCollectionInDB()
        }
    }

    private suspend fun getAllCollectionInDB() {
        collectionsUseCases.getCollectionsInDB().collect { listCollections ->
            _state.value = _state.value.copy(listCollections = listCollections)
        }
    }

    suspend fun addCollectionInDB(collectionDB: CollectionDB) {
        collectionsUseCases.addCollection(collectionDB)
    }

    fun getCollection(type: String) {
        when (type) {
            TitleCollections.TOP_POPULAR_MOVIES.name -> {
                if (state.value.popularMovies == null) {
                    val lazyResult = collectionsUseCases.getCollections(type = type)
                    _state.value = _state.value.copy(popularMovies = lazyResult)
                }
            }

            TitleCollections.POPULAR_SERIES.name -> {
                if (state.value.popularSerials == null) {
                    val lazyResult = collectionsUseCases.getCollections(type = type)
                    _state.value = _state.value.copy(popularSerials = lazyResult)
                }
            }
        }
    }

}