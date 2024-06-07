package com.example.kinopoisk.domain.usecases.common

import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class ApiCount(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(): DailyQuota {
        return kinopoiskRepository.getApiCount()
    }
}