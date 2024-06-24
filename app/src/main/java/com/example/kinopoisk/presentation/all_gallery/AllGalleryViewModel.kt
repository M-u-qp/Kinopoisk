package com.example.kinopoisk.presentation.all_gallery

import androidx.lifecycle.ViewModel
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllGalleryViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
): ViewModel() {


}