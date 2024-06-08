package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class AddCollection(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(collectionDB: CollectionDB) {
        kinopoiskRepository.addCollection(collectionDB)
    }
}