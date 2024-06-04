package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.model.TotalQuota

data class ApiKeyCountResponse(
    val accountType: String,
    val dailyQuota: DailyQuota,
    val totalQuota: TotalQuota
)
