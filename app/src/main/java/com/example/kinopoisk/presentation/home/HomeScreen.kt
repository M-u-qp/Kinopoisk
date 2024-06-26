package com.example.kinopoisk.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.presentation.Dimens.MediumFontSize1
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.home.components.MoviesListCollection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    movies: LazyPagingItems<CollectionItem>,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Int) -> Unit,
    navigateToAllMovies: (List<CollectionItem>) -> Unit
) {

    val state = viewModel.state.collectAsState().value
    TitleCollectionsDB.entries.forEach { collectionToAdd ->
        if (state.listCollections.none { collectionDB -> collectionDB.nameCollection == collectionToAdd.value }) {
            LaunchedEffect(true) {
                withContext(Dispatchers.IO) {
                    viewModel.addCollectionInDB(CollectionDB(nameCollection = collectionToAdd.value))
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MediumPadding2, start = MediumPadding2)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = MediumPadding2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = MediumFontSize1
                ),
                text = stringResource(id = R.string.app_name),
                color = colorResource(id = R.color.black_text)
            )
            IconButton(onClick = { navigateToSearch() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = null
                )
            }
        }

        MoviesListCollection(
            modifier = Modifier.padding(top = MediumPadding1),
            movies = movies,
            onClick = { navigateToDetails(it) },
            navigateToAllMovies = navigateToAllMovies
        )
    }
}