package com.example.kinopoisk.presentation.details.details_staff

import com.example.kinopoisk.domain.model.StaffInfo

data class StaffState(
    val staffInfo: StaffInfo? = null,
    val error: String? = null
)
