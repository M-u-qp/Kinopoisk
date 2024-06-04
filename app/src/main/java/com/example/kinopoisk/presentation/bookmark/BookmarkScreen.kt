package com.example.kinopoisk.presentation.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.MediumFontSize1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding3
import com.example.kinopoisk.presentation.bookmark.components.MoviesListDatabase

@Composable
fun BookmarkScreen(
    state: BookmarkState,
    navigateToDetails: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(top = MediumPadding2)
            .padding(horizontal = MediumPadding2)
    ) {
        Text(
            text = "Хочу посмотреть",
            style = MaterialTheme.typography.displayMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MediumFontSize1
            ),
            color = colorResource(id = R.color.black_text)
        )
        Spacer(modifier = Modifier.height(MediumPadding3))

        MoviesListDatabase(
            movies = state.movies,
            onClick = { navigateToDetails(it.kinopoiskId) }
        )
    }
}