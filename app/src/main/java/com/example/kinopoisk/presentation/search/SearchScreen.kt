package com.example.kinopoisk.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.common.SearchBar
import com.example.kinopoisk.presentation.search.components.MoviesListFilterSearch
import com.example.kinopoisk.presentation.search.components.MoviesListSearch
import com.example.kinopoisk.presentation.search_filter.FilterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Int) -> Unit,
    navigateToSearchFilter: () -> Unit,
    filterData: FilterData?
) {

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            if (filterData != null) {
                event(
                    SearchEvent.UpdateFilterQueryAndSearch(
                        ratingFrom = filterData.ratingFrom,
                        ratingTo = filterData.ratingTo,
                        selectedCountry = filterData.selectedCountry,
                        selectedGenre = filterData.selectedGenre,
                        yearsFrom = filterData.yearsFrom,
                        yearsTo = filterData.yearsTo,
                        typeSearchFilter = filterData.typeSearchFilter,
                        sortSearchFilter = filterData.sortSearchFilter
                    )
                )
            }
        }
    }

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
            onSearch = {
                viewModel.updateFilterMovies()
                event(SearchEvent.SearchMovies)
            },
            navigateToScreenFilter = navigateToSearchFilter
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        if (state.filterMovies != null && filterData != null) {
            state.filterMovies.let { pagingFilterMovies ->
                val filterMovies = pagingFilterMovies.collectAsLazyPagingItems()
                MoviesListFilterSearch(
                    filterMovies = filterMovies,
                    onClick = { (navigateToDetails(it.filmId)) },
                    state = state,
                    viewed = filterData.viewedMovies
                )
            }
        } else {
            state.movies?.let { pagingSearchMovies ->
                val movies = pagingSearchMovies.collectAsLazyPagingItems()
                MoviesListSearch(
                    movies = movies,
                    onClick = { navigateToDetails(it.filmId) },
                )
            }
        }
    }
}