package com.example.kinopoisk.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.common.MoviesListSearch
import com.example.kinopoisk.presentation.common.SearchBar
import com.example.kinopoisk.presentation.navgraph.Route

@Composable
fun SearchScreen(
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigate: (String) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(top = MediumPadding2, start = MediumPadding2, end = MediumPadding2)
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        SearchBar(
            text = state.keyword,
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = { event(SearchEvent.SearchMovies) })

        Spacer(modifier = Modifier.height(MediumPadding1))

        state.movies?.let {
            val movies = it.collectAsLazyPagingItems()
            MoviesListSearch(
                movies = movies,
                onClick = { navigate(Route.DetailsScreen.route) }
            )
        }
    }
}