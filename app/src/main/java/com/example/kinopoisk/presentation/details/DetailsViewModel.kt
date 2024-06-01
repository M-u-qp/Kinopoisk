package com.example.kinopoisk.presentation.details

import androidx.lifecycle.ViewModel
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {

    suspend fun getMovie(id:Int) {
        moviesUseCases.getMovie(id)
    }
}