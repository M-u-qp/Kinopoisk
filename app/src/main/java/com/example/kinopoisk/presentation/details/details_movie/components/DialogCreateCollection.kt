package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.details.details_movie.DetailsViewModel
import kotlinx.coroutines.launch

@Composable
fun DialogCreateCollection() {

    val showDialog = remember { mutableStateOf(false) }
    val viewModel: DetailsViewModel = hiltViewModel()
    val collectionName = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {
        showDialog.value = false
        viewModel.updateShowDialogForCreateCollection(showDialog.value)
    }) {
        Card(
            modifier = Modifier
                .alpha(0.9f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(Dimens.MediumRoundedCornerShape1),
        ) {
            Column(
                modifier = Modifier
                    .padding(Dimens.MediumPadding2),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = collectionName.value,
                    onValueChange = { collectionName.value = it },
                    label = {
                        Text(
                            text = stringResource(id = R.string.Enter_name_your_collection),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = colorResource(id = R.color.gray_text)
                            )
                        )
                    },
                    maxLines = 1
                )
                Button(
                    modifier = Modifier.padding(top = Dimens.SmallPadding1),
                    enabled = collectionName.value.isNotEmpty() &&
                            !(TitleCollectionsDB.entries.any { it.value == collectionName.value }),
                    onClick = {
                        if (collectionName.value.isNotEmpty() &&
                            !(TitleCollectionsDB.entries.any { it.value == collectionName.value })
                        ) {
                            scope.launch {
                                viewModel.addCollectionInDB(CollectionDB(nameCollection = collectionName.value))
                            }
                        }
                        showDialog.value = false
                        viewModel.updateShowDialogForCreateCollection(showDialog.value)
                    }) {
                    Text(
                        text = stringResource(id = R.string.Create),
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