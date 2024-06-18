package com.example.kinopoisk.presentation.collectiondb

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.LargePadding2
import com.example.kinopoisk.presentation.Dimens.MediumFontSize1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.collectiondb.components.MovieCardCollectionDB

@Composable
fun CollectionDBScreen(
    state: CollectionDBState,
    viewModel: CollectionDBViewModel = hiltViewModel(),
    nameCollection: String,
    navigateToDetails: (Int) -> Unit,
    navigateUp: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.getMoviesCollection(nameCollection)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = MediumPadding2)
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

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = SmallPadding1),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = nameCollection,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumFontSize1,
                    color = colorResource(id = R.color.black_text)
                )
            )
        }

        LazyColumn(
            modifier = Modifier.padding(top = LargePadding2),
            verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            items(state.moviesCollection) { movie ->
                movie?.let {
                    MovieCardCollectionDB(
                        movie = it,
                        onClick = { navigateToDetails(movie.kinopoiskId) }
                    )
                }

            }
        }
    }
}