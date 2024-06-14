package com.example.kinopoisk.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.utils.Resource
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
    private val collectionsUseCases: CollectionsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

    var sideEffect by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            getAllCollection()
        }

    }

    suspend fun addCollectionInDB(collectionDB: CollectionDB) {
        collectionsUseCases.addCollection(collectionDB)
    }

    private suspend fun getAllCollection() {
        collectionsUseCases.getCollectionsInDB().collect {
            _state.value = _state.value.copy(
                listCollections = it
            )
        }
    }

//    private suspend fun getCollection(collectionName: String) {
//        collectionsUseCases.getCollectionInDB(collectionName).collect {
//            _state.value = _state.value.copy(listMovie = it)
//        }
//    }

    suspend fun getAllMoviesInDB() {
        moviesUseCases.getAllMoviesInDB().collect {
            _state.value = _state.value.copy(listMovie = it)
        }
    }


    fun onEvent(event: DetailsEvent) {
        when (event) {
            //Клик по иконке Закладка ( добавить/удалить фильм из Хочу посмотреть )
            is DetailsEvent.ReadyToViewMovie -> {
                addDeleteMovieInDB(event.movie, TitleCollectionsDB.READY_TO_VIEW.value)
            }

            //Клик по иконке Лайк ( добавить/удалить фильм из Понравилось )
            is DetailsEvent.FavoriteMovie -> {
                addDeleteMovieInDB(event.movie, TitleCollectionsDB.FAVORITE.value)
            }

            //Клик по иконке Троеточие ( выбор своей коллекции из списка в диалоге )
            is DetailsEvent.AddMovieInCollection -> {
                val selectedCollection = state.value.selectedCollection
                if (selectedCollection.isNotEmpty()) {
                    addDeleteMovieInDB(event.movie, selectedCollection)
                    _state.value = _state.value.copy(
                        selectedCollection = "",
                        showDialogForCollections = false
                    )
                } else {
                    _state.value = _state.value.copy(showDialogForCollections = true)
                }
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private fun addDeleteMovieInDB(movie: Movie, collectionName: String) {
        viewModelScope.launch {
            val movieForDB = movie.copy(collectionName = collectionName)
            if (state.value.listMovie.isEmpty()) {
                upsertMovie(movieForDB)
            } else {
                var shouldAddMovie = true
                state.value.listMovie.forEach { existingMovie ->
                    if (movie.kinopoiskId == existingMovie?.kinopoiskId && existingMovie.collectionName == collectionName) {
                        deleteMovie(existingMovie.id)
                        shouldAddMovie = false
                    }
                }
                if (shouldAddMovie) {
                    upsertMovie(movieForDB)
                }
            }
        }
    }

    fun updateShowDialog(isVisible: Boolean) {
        _state.value = _state.value.copy(showDialogForCollections = isVisible)
    }

    fun updateSelectedCollection(selectedCollection: String) {
        _state.value = _state.value.copy(selectedCollection = selectedCollection)
    }

    private suspend fun deleteMovie(id: Int) {
        moviesUseCases.deleteMovieById(id = id)
        sideEffect = "Фильм удален"
    }

    private suspend fun upsertMovie(movie: Movie) {
        moviesUseCases.upsertMovie(movie = movie)
        sideEffect = "Фильм сохранен"
    }

    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loadingMovie = true)
            val movie = moviesUseCases.getMovie(movieId)
            when (movie) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loadingMovie = false,
                        movie = movie.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        loadingMovie = false,
                        error = movie.exception.toString()
                    )
                }

                else -> Unit
            }
        }
    }
}