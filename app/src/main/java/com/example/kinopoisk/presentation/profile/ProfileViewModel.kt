package com.example.kinopoisk.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases,
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    init {
        viewModelScope.launch {
            getAllCollections()
        }
    }

    suspend fun deleteCollection(
        collectionName: String,
        collectionDB: CollectionDB
    ) {
        collectionsUseCases.deleteCollection(collectionDB)
        moviesUseCases.deleteCollectionMovies(collectionName)
    }

    private suspend fun getAllCollections() {
        collectionsUseCases.getCollectionsInDB().collect { listCollections ->
            _state.value = _state.value.copy(allCollections = listCollections)
        }
    }

    suspend fun getCollection(nameCollection: String) {
        collectionsUseCases.getCollectionInDB(nameCollection).collect {
            _state.value = _state.value.copy(
                listCollectionsAndSize = _state.value.listCollectionsAndSize.toMutableMap()
                    .apply { put(nameCollection, it) })
        }
    }

    suspend fun addCollectionInDB(collectionDB: CollectionDB) {
        collectionsUseCases.addCollection(collectionDB)
    }

    fun updateShowDialogForCreateCollection(show: Boolean) {
        _state.value = _state.value.copy(showDialogForCreateCollection = show)
    }

    fun updateShowDialogAreYouSure(show: Boolean) {
        _state.value = _state.value.copy(showDialogAreYouSure = show)
    }
}