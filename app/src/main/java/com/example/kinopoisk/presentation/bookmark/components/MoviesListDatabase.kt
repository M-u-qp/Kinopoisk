package com.example.kinopoisk.presentation.bookmark.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.Dimens

@Composable
fun MoviesListDatabase(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    onClick: (Movie) -> Unit
) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2),
            contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding3)
        ) {
            items(count = movies.size) { index ->
               val movie = movies[index]
                    MovieCardDatabase(movie = movie, onClick = { onClick(movie) })
            }
        }

}