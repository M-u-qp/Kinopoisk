package com.example.kinopoisk.presentation.all_movies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.PremieresItem
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.common.CommonMovieCard
import com.example.kinopoisk.presentation.common.EmptyScreen

@Composable
fun AllMoviesScreen(
    movies: List<Any>,
    navigateToDetails: (Int) -> Unit,
    navigateUp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Dimens.MediumPadding2)
            .statusBarsPadding()
    ) {
        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_back_arrow
                ),
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(Dimens.SmallPadding1))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2)
        ) {
            items(movies) { item ->
                when (item) {
                    is Movie -> {
                        CommonMovieCard(
                            movie = item,
                            onClick = { navigateToDetails(item.kinopoiskId) }
                        )
                    }

                    is SimilarItem -> {
                        item.filmId?.let { id ->
                            CommonMovieCard(
                                movie = item,
                                onClick = { navigateToDetails(id) }
                            )
                        }
                    }

                    is CollectionItem -> {
                        CommonMovieCard(
                            movie = item,
                            onClick = { navigateToDetails(item.kinopoiskId) }
                        )
                    }

                    is PremieresItem -> {
                        CommonMovieCard(
                            movie = item,
                            onClick = { navigateToDetails(item.kinopoiskId) }
                        )
                    }

                    is FilterItem -> {
                        CommonMovieCard(
                            movie = item,
                            onClick = { navigateToDetails(item.kinopoiskId) }
                            )
                    }

                    else -> {
                        EmptyScreen()
                    }
                }

            }
        }
    }
}