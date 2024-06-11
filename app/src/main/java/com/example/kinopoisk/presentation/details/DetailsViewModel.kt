package com.example.kinopoisk.presentation.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.utils.Resource
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
            getCollection("Хочу посмотреть")
        }
    }

    private suspend fun getCollection(collectionName: String) {
        collectionsUseCases.getCollectionInDB(collectionName).collect {
            _state.value = _state.value.copy(
                listMovie = it
            )
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteMovie -> {
                viewModelScope.launch {
                    Log.d("TAG", "${state.value.listMovie}")
                    val movieForDB = event.movie.copy(id = 1, collectionName = "Хочу посмотреть")
                    if (state.value.listMovie.isEmpty()) {
                        upsertMovie(movieForDB)
                    } else {
                        state.value.listMovie.onEach { movie ->
                            if (event.movie.kinopoiskId == movie?.kinopoiskId) {
                                deleteMovie(
                                    movie.id
                                )
                            } else {
                                val dbIdMovie = state.value.listMovie.last()?.id
                                upsertMovie(
                                    movieForDB
                                )
                            }
                        }
                    }

                }
            }

            is DetailsEvent.LikeMovie -> {}
            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private fun changeCollectionName() {

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