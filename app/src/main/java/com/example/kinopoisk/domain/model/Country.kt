package com.example.kinopoisk.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val country: String
): Parcelable