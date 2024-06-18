package com.example.kinopoisk.domain.usecases.staff

import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetListStaff(
    private val kinopoiskRepository: KinopoiskRepository
) {
    suspend operator fun invoke(filmId: Int): Resource<List<Staff>> {
        return kinopoiskRepository.getListStaff(filmId = filmId)
    }
}