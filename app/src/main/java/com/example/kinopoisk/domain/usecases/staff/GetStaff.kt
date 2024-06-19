package com.example.kinopoisk.domain.usecases.staff

import com.example.kinopoisk.domain.model.StaffInfo
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetStaff(
    private val kinopoiskRepository: KinopoiskRepository
) {
    suspend operator fun invoke(id: Int): Resource<StaffInfo> {
        return kinopoiskRepository.getStaff(id = id)
    }
}