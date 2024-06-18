package com.example.kinopoisk.presentation.collectiondb

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.presentation.collectiondb.components.MovieCardCollectionDB

@Composable
fun CollectionDBScreen(
    state: CollectionDBState,
    viewModel: CollectionDBViewModel = hiltViewModel(),
    nameCollection: String
) {

    LaunchedEffect(key1 = true) {
        viewModel.getMoviesCollection(nameCollection)
    }

    LazyColumn {
        items(state.moviesCollection) { movie ->
            movie?.let {
                MovieCardCollectionDB(
                    movie = it,
                    onClick = {}
                )
            }

        }
    }
}