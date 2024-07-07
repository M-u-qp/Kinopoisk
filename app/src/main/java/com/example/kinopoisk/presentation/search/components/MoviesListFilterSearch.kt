package com.example.kinopoisk.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.data.mapper.toSearchFilm
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.SearchFilm
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.search.SearchState

@Composable
fun MoviesListFilterSearch(
    modifier: Modifier = Modifier,
    filterMovies: LazyPagingItems<FilterItem>,
    onClick: (SearchFilm) -> Unit,
    state: SearchState,
    viewed: Boolean
) {

    val handlePagingResultFilterSearch = handlePagingResultSearch(movies = filterMovies)

    if (handlePagingResultFilterSearch) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2),
            contentPadding = PaddingValues(all = Dimens.ExtraSmallPadding3)
        ) {
            if (filterMovies.itemSnapshotList.isNotEmpty()) {
                items(count = filterMovies.itemCount) { index ->
                    if (viewed) {
                        filterMovies.itemSnapshotList.items.filter { movie ->
                            !state.moviesCollection.any { it?.kinopoiskId == movie.kinopoiskId }
                        }[index].let {
                            MovieCardSearch(
                                film = it.toSearchFilm(),
                                onClick = { onClick(it.toSearchFilm()) })
                        }
                    } else {
                        filterMovies.itemSnapshotList.items[index].let {
                            MovieCardSearch(
                                film = it.toSearchFilm(),
                                onClick = { onClick(it.toSearchFilm()) })
                        }
                    }
                }
            }
        }
    }
}