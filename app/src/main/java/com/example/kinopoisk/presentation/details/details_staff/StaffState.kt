package com.example.kinopoisk.presentation.details.details_staff

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.StaffFilms
import com.example.kinopoisk.domain.model.StaffInfo

data class StaffState(
    val staffInfo: StaffInfo? = null,
    val filterBestMovies: List<StaffFilms> = emptyList(),
    val errorStaff: String? = null,
    val listBestMovies: MutableList<Movie?> = mutableListOf(),
    val errorMovie: String? = null,
    var isShowDialogFilmography: Boolean = false,
    var isLoadingMovies: Boolean = false
)
