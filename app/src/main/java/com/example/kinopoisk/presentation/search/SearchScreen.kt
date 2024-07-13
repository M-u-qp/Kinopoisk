package com.example.kinopoisk.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2
import com.example.kinopoisk.presentation.common.SearchBar
import com.example.kinopoisk.presentation.common.SortSearchFilter
import com.example.kinopoisk.presentation.common.TypeSearchFilter
import com.example.kinopoisk.presentation.common.listLast100Years
import com.example.kinopoisk.presentation.search.components.MoviesListFilterSearch
import com.example.kinopoisk.presentation.search_filter.FilterData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Int) -> Unit,
    navigateToSearchFilter: (String) -> Unit,
    filterData: FilterData?
) {

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            if (filterData != null) {
                event(
                    SearchEvent.UpdateFilterQuery(
                        ratingFrom = filterData.ratingFrom,
                        ratingTo = filterData.ratingTo,
                        selectedCountry = filterData.selectedCountry,
                        selectedGenre = filterData.selectedGenre,
                        yearsFrom = filterData.yearsFrom,
                        yearsTo = filterData.yearsTo,
                        typeSearchFilter = filterData.typeSearchFilter,
                        sortSearchFilter = filterData.sortSearchFilter,
                        viewedMovies = filterData.viewedMovies,
                        keyword = filterData.keyword
                    )
                )
                event(SearchEvent.SearchMovies)
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
            text = state.keyword ?: "",
            readOnly = false,
            onValueChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onSearch = {
                event(SearchEvent.SearchMovies)
            },
            navigateToScreenFilter = { navigateToSearchFilter(state.keyword ?: "keyword") },
            state = state
        )

        TextButton(
            modifier = Modifier.align(Alignment.End),
            onClick = {
            event(SearchEvent.UpdateFilterQuery(
                ratingFrom = 0,
                ratingTo = 10,
                selectedCountry = emptyList(),
                selectedGenre = emptyList(),
                yearsFrom = listLast100Years().last(),
                yearsTo = listLast100Years().first(),
                typeSearchFilter = TypeSearchFilter.ALL.name,
                sortSearchFilter = SortSearchFilter.YEAR.name,
                keyword = state.keyword ?: "",
                viewedMovies = false
            )
            )
            viewModel.updateResetFilter(true)
        }) {
            Text(
                text = stringResource(id = R.string.Reset_filter),
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = SmallFontSize2,
                    color = if (!state.resetFilter)colorResource(id = R.color.gray_text)
                    else MaterialTheme.colorScheme.primary
                )
            )
        }

        Spacer(modifier = Modifier.height(MediumPadding1))

        state.filterMovies?.let { pagingFilterMovies ->
            val filterMovies = pagingFilterMovies.collectAsLazyPagingItems()
            MoviesListFilterSearch(
                filterMovies = filterMovies,
                onClick = { (navigateToDetails(it.filmId)) },
                state = state
            )
        }

    }
}