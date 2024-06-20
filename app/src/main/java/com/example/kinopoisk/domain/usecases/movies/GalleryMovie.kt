package com.example.kinopoisk.domain.usecases.movies

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class GalleryMovie(
    private val kinopoiskRepository: KinopoiskRepository
) {

     operator fun invoke(id: Int, type: String): Flow<PagingData<GalleryItem>> {
        return kinopoiskRepository.getGalleryMovie(id = id, type = type)
    }
}