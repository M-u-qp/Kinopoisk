package com.example.kinopoisk.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.LargePadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding4
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.DialogAreYouSure
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.profile.components.CollectionCard
import com.example.kinopoisk.presentation.profile.components.DialogCreateCollection
import com.example.kinopoisk.presentation.profile.components.MovieCardProfile
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    state: ProfileState,
    viewModel: ProfileViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit
) {
    val scope = rememberCoroutineScope()
    val sizeViewedCollection =
        state.listCollectionsAndSize[TitleCollectionsDB.VIEWED.value]?.size ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = MediumPadding2, top = LargePadding2)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.Viewed),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colorResource(id = R.color.black_text)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = (sizeViewedCollection).toString(),
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = SmallFontSize1,
                        fontWeight = FontWeight.Medium
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.scale(1.5f),
                        painter = painterResource(id = R.drawable.ic_open_list),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

            }
        }

        LazyRow(
            modifier = Modifier
                .padding(top = MediumPadding1),
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
        }

        Spacer(modifier = Modifier.height(MediumPadding4))

        Text(
            modifier = Modifier,
            text = stringResource(id = R.string.Collections),
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = colorResource(id = R.color.black_text)
        )
        Row(
            modifier = Modifier
                .padding(vertical = SmallPadding1)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.updateShowDialogForCreateCollection(true) }) {
                Icon(
                    modifier = Modifier.scale(1.5f),
                    painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                    contentDescription = null
                )
            }
            Text(
                modifier = Modifier.padding(start = SmallPadding1),
                text = stringResource(id = R.string.Create_collection),
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = colorResource(id = R.color.black_text)
            )
        }

        if (state.showDialogForCreateCollection) {
            DialogCreateCollection()
        }

        LazyVerticalGrid(
            modifier = Modifier.padding(end = MediumPadding2),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
            verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            items(state.allCollections) { item ->
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
                            onClickDelete = {}
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
                            onClickDelete = {}
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
                            }
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
    }
}