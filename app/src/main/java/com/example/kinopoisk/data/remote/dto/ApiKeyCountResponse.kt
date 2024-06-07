package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.model.TotalQuota

data class ApiKeyCountResponse(
    val totalQuota: TotalQuota,
    val dailyQuota: DailyQuota,
    val accountType: String
)
