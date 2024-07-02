package com.example.kinopoisk.presentation.all_seasons

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.kinopoisk.domain.model.SeasonsItem
import com.example.kinopoisk.presentation.common.NavigateUpButton

@Composable
fun AllSeasonsScreen(
    serialName: String,
    seasonsItem: List<SeasonsItem>,
    navigateUp: () -> Unit
) {

    Column(

    ) {
        NavigateUpButton(navigateUp = navigateUp, text = serialName)


    }
}