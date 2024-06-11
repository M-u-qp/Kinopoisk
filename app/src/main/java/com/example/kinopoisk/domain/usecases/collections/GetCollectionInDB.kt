package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionInDB(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(collectionName: String): Flow<List<Movie?>> {
        return kinopoiskRepository.selectCollection(collectionName = collectionName)
    }
}