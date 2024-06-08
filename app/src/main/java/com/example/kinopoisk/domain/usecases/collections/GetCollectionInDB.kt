package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class GetCollectionInDB(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(nameCollection: String): CollectionDB? {
        return kinopoiskRepository.selectCollection(nameCollection = nameCollection)
    }
}