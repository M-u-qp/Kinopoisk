package com.example.kinopoisk.domain.usecases.collections

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class GetCollections(
    private val kinopoiskRepository: KinopoiskRepository
) {

    operator fun invoke(type: String): Flow<PagingData<CollectionItem>>{
        return kinopoiskRepository.getTopPopularAll(type = type)
    }
}