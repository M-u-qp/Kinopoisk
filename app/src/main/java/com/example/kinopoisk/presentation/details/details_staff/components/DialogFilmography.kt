package com.example.kinopoisk.presentation.details.details_staff.components

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.details.details_staff.StaffState
import com.example.kinopoisk.presentation.details.details_staff.StaffViewModel

@Composable
fun DialogFilmography(
    state: StaffState,
    navigateToDetails: (Int) -> Unit
) {

    val showDialog = remember { mutableStateOf(false) }
    val viewModel: StaffViewModel = hiltViewModel()

    Dialog(onDismissRequest = {
        showDialog.value = false
        viewModel.updateVisibleDialogFilmography(showDialog.value)
    }) {
        Card(
            modifier = Modifier
                .padding(Dimens.MediumPadding2)
                .alpha(0.9f)
                .fillMaxWidth()
                .height(Dimens.DialogHeight),
            shape = RoundedCornerShape(Dimens.MediumRoundedCornerShape1),
        ) {

            Box(
                modifier = Modifier
                    .padding(top = Dimens.SmallPadding1)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.Filmography),
                    style = MaterialTheme.typography.displaySmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = Dimens.MediumFontSize3,
                        color = colorResource(id = R.color.black_text)
                    )
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
            ) {
                state.staffInfo?.films?.let { listFilms ->
                    items(listFilms) { film ->
                        TextButton(onClick = {
                            showDialog.value = false
                            viewModel.updateVisibleDialogFilmography(showDialog.value)
                            film.filmId?.let { id ->
                                navigateToDetails(id)
                            }
                        }) {
                            Text(
                                text = film.nameRu ?: film.nameEn ?: "",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = Dimens.MediumFontSize2
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}