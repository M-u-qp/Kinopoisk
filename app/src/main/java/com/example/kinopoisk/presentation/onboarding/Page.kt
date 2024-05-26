package com.example.kinopoisk.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.kinopoisk.R

data class Page(
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        description = "Узнавай\n\n о премьерах",
        image = R.drawable.onboarding1
    ),
    Page(
        description = "Создавай\n\n коллекции",
        image = R.drawable.onboarding2
    ),
    Page(
        description = "Делись\n\n с друзьями",
        image = R.drawable.onboarding3
    )
)