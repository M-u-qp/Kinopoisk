package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.SeasonsItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetSerialSeasons(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(id: Int): Resource<List<SeasonsItem>> {
        return kinopoiskRepository.getSerialSeasons(id)
    }
}