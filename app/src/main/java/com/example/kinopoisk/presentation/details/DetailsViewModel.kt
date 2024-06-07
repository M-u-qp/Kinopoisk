package com.example.kinopoisk.presentation.details

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
) : ViewModel() {

    private val _state = mutableStateOf(DetailsState())
    val state: State<DetailsState> = _state

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.UpsertDeleteMovie -> {
                viewModelScope.launch {
                    val movie = moviesUseCases.selectMovie(event.movie.kinopoiskId)
                    if (movie == null) {
                        upsertMovie(event.movie)
                    } else {
                        deleteMovie(event.movie)
                    }
                }
            }

            is DetailsEvent.LikeMovie -> {}
            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    private suspend fun deleteMovie(movie: Movie) {
        moviesUseCases.deleteMovie(movie = movie)
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