package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ErrorDialogSizeHeight
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.details.details_movie.DetailsViewModel

@Composable
fun ErrorDialog(
    viewModel: Any,
    text: String
) {
    when (viewModel) {
        is DetailsViewModel -> {
            var showDialog by remember { mutableStateOf(false) }

            Dialog(
                onDismissRequest = {
                    showDialog = false
                    viewModel.updateShowErrorDialog(false)
                },
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = MediumPadding2, start = MediumPadding2)
                        .fillMaxWidth()
                        .height(ErrorDialogSizeHeight),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.Error_dialog),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = Dimens.MediumFontSize3
                            ),
                        )

                        IconButton(
                            modifier = Modifier
                                .padding(end = Dimens.SmallPadding1),
                            onClick = {
                                viewModel.updateShowErrorDialog(false)
                            }
                        ) {
                            Icon(
                                modifier = Modifier.size(Dimens.IconSize),
                                painter = painterResource(id = R.drawable.ic_close),
                                contentDescription = null
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(MediumPadding2))

                    Text(
                        text = text + stringResource(id = R.string.error_add_collection),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = colorResource(id = R.color.gray_text),
                            fontSize = Dimens.MediumFontSize2
                        ),
                    )
                }
            }
        }
    }
}