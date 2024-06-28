package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetCountriesAndGenres(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(): Resource<CountriesAndGenres> {
        return kinopoiskRepository.getCountriesAndGenres()
    }
}