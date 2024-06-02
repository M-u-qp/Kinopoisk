package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class SelectMovie(
    private val moviesDao: MoviesDao
) {

    suspend operator fun invoke(kinopoiskId: Int): Movie? {
        return moviesDao.getMovie(kinopoiskId = kinopoiskId)
    }
}