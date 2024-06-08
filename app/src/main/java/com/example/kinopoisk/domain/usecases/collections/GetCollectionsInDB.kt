package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class GetCollectionsInDB(
    private val kinopoiskRepository: KinopoiskRepository
) {

    operator fun invoke(): Flow<List<CollectionDB>> {
        return kinopoiskRepository.selectCollections()
    }
}
