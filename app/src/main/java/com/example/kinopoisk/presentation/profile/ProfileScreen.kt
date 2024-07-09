package com.example.kinopoisk.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.LargePadding1
import com.example.kinopoisk.presentation.Dimens.MediumFontSize2
import com.example.kinopoisk.presentation.Dimens.MediumFontSize3
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding4
import com.example.kinopoisk.presentation.Dimens.MovieCardSizeHeight
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.DialogAreYouSure
import com.example.kinopoisk.presentation.common.ErrorDialog
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.common.TitleCommon
import com.example.kinopoisk.presentation.profile.components.CollectionCard
import com.example.kinopoisk.presentation.profile.components.DialogCreateCollection
import com.example.kinopoisk.presentation.profile.components.MovieCardProfile
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    state: ProfileState,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit,
    navigateToCollection: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sizeViewedCollection =
        state.listCollectionsAndSize[TitleCollectionsDB.VIEWED.value]?.size ?: 0
    val sizeInterestingCollection =
        state.listCollectionsAndSize[TitleCollectionsDB.INTERESTING.value]?.size ?: 0
    val scrollState = rememberScrollState()

    if (state.showDialogForCreateCollection) {
        DialogCreateCollection()
    }

    if (state.showErrorDialog) {
        ErrorDialog(viewModel = viewModel, text = state.errorCollectionName)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getCollection(TitleCollectionsDB.VIEWED.value)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getCollection(TitleCollectionsDB.INTERESTING.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = MediumPadding2, top = LargePadding1)
            .verticalScroll(state = scrollState)
    ) {

        TitleCommon(
            nameTitle = TitleCollectionsDB.VIEWED.value,
            varParam = sizeViewedCollection.toString(),
            onClick = { navigateToCollection(TitleCollectionsDB.VIEWED.value) }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            if (sizeViewedCollection != 0) {
                state.listCollectionsAndSize[TitleCollectionsDB.VIEWED.value]?.let { collection ->
                    items(collection) {
                        it?.let { movie ->
                            MovieCardProfile(
                                movie = movie,
                                onClick = { navigateToDetails(movie.kinopoiskId) }
                            )
                        }
                    }
                }
            }

            if (sizeViewedCollection != 0) {
                //Иконка очистки коллекции
                item {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = SmallPadding1,
                                end = SmallPadding1
                            )
                            .height(MovieCardSizeHeight),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                viewModel.deleteMoviesInCollection(collectionName = TitleCollectionsDB.VIEWED.value)
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.Clear_history),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = SmallFontSize2,
                                color = colorResource(id = R.color.black_text)
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(MediumPadding4))

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Collections),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MediumFontSize3
            ),
            color = colorResource(id = R.color.black_text)
        )
        Row(
            modifier = Modifier
                .padding(vertical = SmallPadding1)
                .fillMaxWidth()
                .clickable { viewModel.updateShowDialogForCreateCollection(true) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.scale(1.5f),
                painter = painterResource(
                    id = R.drawable.ic_add
                ),
                contentDescription = null
            )
            Text(
                modifier = Modifier.padding(start = SmallPadding1),
                text = stringResource(id = R.string.Create_collection),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    fontSize = MediumFontSize2
                ),
                color = colorResource(id = R.color.black_text)
            )
        }

        val listCollections = state.allCollections.filter { collection ->
            collection.nameCollection != TitleCollectionsDB.INTERESTING.value &&
            collection.nameCollection != TitleCollectionsDB.VIEWED.value
        }

        LazyHorizontalGrid(
            modifier = Modifier
                .padding(end = MediumPadding2)
                .heightIn(max = Dimens.LazyVerticalGridHeight),
            rows = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
            verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            items(listCollections) { item ->
                val sizeCollection = state.listCollectionsAndSize[item.nameCollection]?.size ?: 0
                when (item.nameCollection) {
                    TitleCollectionsDB.READY_TO_VIEW.value -> {
                        LaunchedEffect(key1 = true) {
                            viewModel.getCollection(item.nameCollection)
                        }
                        CollectionCard(
                            icon = R.drawable.ic_bookmark_border,
                            nameCollection = item.nameCollection,
                            sizeCollection = sizeCollection,
                            onClickDelete = {},
                            navigateToCollection = navigateToCollection
                        )
                    }

                    TitleCollectionsDB.FAVORITE.value -> {
                        LaunchedEffect(key1 = true) {
                            viewModel.getCollection(item.nameCollection)
                        }
                        CollectionCard(
                            icon = R.drawable.ic_like_border,
                            nameCollection = item.nameCollection,
                            sizeCollection = sizeCollection,
                            onClickDelete = {},
                            navigateToCollection = navigateToCollection
                        )
                    }

                    else -> {
                        LaunchedEffect(key1 = true) {
                            viewModel.getCollection(item.nameCollection)
                        }
                        CollectionCard(
                            icon = R.drawable.ic_profile_nav,
                            nameCollection = item.nameCollection,
                            sizeCollection = sizeCollection,
                            onClickDelete = {
                                viewModel.updateShowDialogAreYouSure(true)
                            },
                            navigateToCollection = navigateToCollection
                        )

                        if (state.showDialogAreYouSure) {
                            DialogAreYouSure(
                                onClickYes = {
                                    scope.launch {
                                        viewModel.deleteCollection(
                                            collectionName = item.nameCollection,
                                            collectionDB = CollectionDB(
                                                item.id,
                                                item.nameCollection
                                            )
                                        )
                                    }
                                    viewModel.updateShowDialogAreYouSure(false)
                                },
                                onClickNo = { viewModel.updateShowDialogAreYouSure(false) },
                                onDismiss = { viewModel.updateShowDialogAreYouSure(false) }
                            )
                        }
                    }
                }
            }
        }

        TitleCommon(
            modifier = Modifier.padding(top = MediumPadding1),
            nameTitle = TitleCollectionsDB.INTERESTING.value,
            varParam = sizeViewedCollection.toString(),
            onClick = { navigateToCollection(TitleCollectionsDB.INTERESTING.value) }
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            if (sizeInterestingCollection != 0) {
                state.listCollectionsAndSize[TitleCollectionsDB.INTERESTING.value]?.let { collection ->
                    items(collection) {
                        it?.let { movie ->
                            MovieCardProfile(
                                movie = movie,
                                onClick = { navigateToDetails(movie.kinopoiskId) }
                            )
                        }
                    }
                }
            }

            if (sizeInterestingCollection != 0) {
                //Иконка очистки коллекции
                item {
                    Column(
                        modifier = Modifier
                            .padding(
                                start = SmallPadding1,
                                end = SmallPadding1
                            )
                            .height(MovieCardSizeHeight),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = {
                            scope.launch {
                                viewModel.deleteMoviesInCollection(collectionName = TitleCollectionsDB.INTERESTING.value)
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_delete),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.Clear_history),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = SmallFontSize2,
                                color = colorResource(id = R.color.black_text)
                            )
                        )
                    }
                }
            }
        }
    }
}