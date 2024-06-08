package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class DeleteCollection(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(collectionDB: CollectionDB) {
        kinopoiskRepository.deleteCollection(collectionDB)
    }
}