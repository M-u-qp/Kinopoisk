package com.example.kinopoisk.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.DialogHeight
import com.example.kinopoisk.presentation.Dimens.MediumFontSize2
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumRoundedCornerShape1
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.details.DetailsEvent
import com.example.kinopoisk.presentation.details.DetailsState
import com.example.kinopoisk.presentation.details.DetailsViewModel

@Composable
fun DialogAddMovieInCollections(
    state: DetailsState,
    event: (DetailsEvent) -> Unit
) {

    val showDialog = remember { mutableStateOf(false) }
    val viewModel: DetailsViewModel = hiltViewModel()

    Dialog(onDismissRequest = {
        showDialog.value = false
        viewModel.updateShowDialog(showDialog.value)
    }) {
        Card(
            modifier = Modifier
                .padding(MediumPadding2)
                .alpha(0.9f)
                .fillMaxWidth()
                .height(DialogHeight),
            shape = RoundedCornerShape(MediumRoundedCornerShape1),
        ) {

            Box(modifier = Modifier.padding(top = SmallPadding1).fillMaxWidth(),
                contentAlignment = Alignment.Center){
                Text(
                    text = "Выберите вашу коллекцию\n или создайте её в профиле",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontSize = MediumFontSize2,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.black_text)
                    )
                )
            }

            val listCollections = state.listCollections.filter { collection ->
                collection.nameCollection != TitleCollectionsDB.READY_TO_VIEW.value &&
                        collection.nameCollection != TitleCollectionsDB.FAVORITE.value &&
                        collection.nameCollection != TitleCollectionsDB.VIEWED.value
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            ) {
                items(listCollections) { collection ->
                    TextButton(
                        onClick = {
                            viewModel.updateSelectedCollection(collection.nameCollection)
                            showDialog.value = false
                            viewModel.updateShowDialog(showDialog.value)
                            state.movie?.let {
                                event(DetailsEvent.AddMovieInCollection(movie = it))
                            }
                        }) {
                        Text(
                            text = collection.nameCollection,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = MediumFontSize2
                            )
                        )
                    }
                }
            }
        }
    }
}