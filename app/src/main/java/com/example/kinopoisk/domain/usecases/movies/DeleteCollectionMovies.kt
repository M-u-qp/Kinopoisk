package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.repository.KinopoiskRepository

class DeleteCollectionMovies(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(collectionName: String) {
        kinopoiskRepository.deleteCollectionMovies(collectionName)
    }
}