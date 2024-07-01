package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.DialogCardSizeHeight
import com.example.kinopoisk.presentation.Dimens.MediumFontSize2
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumRoundedCornerShape1
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.ErrorDialog
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.details.details_movie.DetailsEvent
import com.example.kinopoisk.presentation.details.details_movie.DetailsState
import com.example.kinopoisk.presentation.details.details_movie.DetailsViewModel

@Composable
fun DialogAddMovieInCollections(
    state: DetailsState,
    event: (DetailsEvent) -> Unit
) {

    val showDialog = remember { mutableStateOf(false) }
    val viewModel: DetailsViewModel = hiltViewModel()

    if (state.showDialogForCreateCollection) {
        DialogCreateCollection()
    }

    if (state.showErrorDialog) {
        ErrorDialog(viewModel = viewModel, text = state.errorCollectionName)
    }

    Dialog(onDismissRequest = {
        showDialog.value = false
        viewModel.updateShowDialog(showDialog.value)
    }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(DialogCardSizeHeight)
                .alpha(0.9f),
            shape = RoundedCornerShape(MediumRoundedCornerShape1),
        ) {

            IconButton(
                modifier = Modifier
                    .padding(top = SmallPadding1, end = SmallPadding1)
                    .align(Alignment.End),
                onClick = {
                    viewModel.updateSelectedCollectionDeleteAll()
                    viewModel.updateShowDialog(false)
                }
            ) {
                Icon(
                    modifier = Modifier.size(Dimens.IconSize),
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = null
                )
            }

            state.movie?.let {
                MovieCardDialogItem(
                    modifier = Modifier.padding(start = MediumPadding2),
                    movie = state.movie
                )
            }

            Spacer(modifier = Modifier.height(MediumPadding1))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(MediumPadding1))
            //Добавить фильм в выбранные коллекции
            Row(
                modifier = Modifier
                    .padding(start = MediumPadding2)
                    .fillMaxWidth()
                    .clickable {
                        if (state.selectedCollection.isNotEmpty()) {
                            state.movie?.let {
                                event(DetailsEvent.AddMovieInCollection(movie = it))
                            }
                            viewModel.updateShowDialog(false)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.scale(1.5f),
                    painter = painterResource(
                        id = R.drawable.ic_add_in_collection
                    ),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(start = SmallPadding1),
                    text = stringResource(id = R.string.Add_in_collection),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = MediumFontSize2
                    ),
                    color = colorResource(id = R.color.black_text)
                )
            }

            Spacer(modifier = Modifier.height(MediumPadding1))
            HorizontalDivider()

            val listCollections = state.listCollections.filter { collection ->
                collection.nameCollection != TitleCollectionsDB.READY_TO_VIEW.value &&
                        collection.nameCollection != TitleCollectionsDB.FAVORITE.value &&
                        collection.nameCollection != TitleCollectionsDB.VIEWED.value
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                items(listCollections) { collection ->
                    LaunchedEffect(key1 = true) {
                        viewModel.getCollectionSize(collection.nameCollection)
                    }
                    val sizeCollection =
                        state.listCollectionsAndSize[collection.nameCollection]?.size ?: 0
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        var checkBoxState by remember { mutableStateOf(false) }
                        Checkbox(checked = checkBoxState, onCheckedChange = {
                            checkBoxState = it
                            if (checkBoxState) {
                                viewModel.updateSelectedCollectionAdd(collection.nameCollection)
                            } else {
                                viewModel.updateSelectedCollectionDelete(collection.nameCollection)
                            }

                        })
                        Text(
                            text = collection.nameCollection,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = MediumFontSize2
                            )
                        )
                        Text(
                            text = sizeCollection.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = MediumFontSize2
                            )
                        )
                    }

                    HorizontalDivider()
                }
                //Создать свою коллекцию
                item {
                    Spacer(modifier = Modifier.height(MediumPadding1))
                    Row(
                        modifier = Modifier
                            .padding(start = MediumPadding2)
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

                    Spacer(modifier = Modifier.height(MediumPadding1))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(MediumPadding1))
                }
            }
        }
    }
}