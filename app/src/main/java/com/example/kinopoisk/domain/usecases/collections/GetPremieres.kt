package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.PremieresItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetPremieres(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(year: Int, month: String): Resource<List<PremieresItem>> {
        return kinopoiskRepository.getPremieres(year, month)
    }
}