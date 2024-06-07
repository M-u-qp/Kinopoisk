package com.example.kinopoisk.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.common.EmptyScreen
import com.example.kinopoisk.presentation.common.MovieCardSearchShimmerEffect

@Composable
fun MoviesListSearch(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Film>,
    onClick: (Film) -> Unit
) {
    val handlePagingResultSearch = handlePagingResultSearch(movies = movies)

    if (handlePagingResultSearch) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2),
            contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding3)
        ) {
            items(count = movies.itemCount) { index ->
                movies[index]?.let {
                    MovieCardSearch(film = it, onClick = { onClick(it) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResultSearch(
    movies: LazyPagingItems<Film>
): Boolean {
    val loadState = movies.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        movies.itemCount == 0 -> {
            EmptyScreen()
            false
        }

        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2)
    ) {
        repeat(10) {
            MovieCardSearchShimmerEffect()
        }
    }
}