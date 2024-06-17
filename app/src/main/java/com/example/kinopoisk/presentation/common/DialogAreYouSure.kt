package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumRoundedCornerShape1
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1

@Composable
fun DialogAreYouSure(
    onClickYes: () -> Unit,
    onClickNo: () -> Unit,
    onDismiss: () -> Unit
) {

    val showDialog = remember { mutableStateOf(onDismiss) }
    Dialog(onDismissRequest = showDialog.value) {
        Card(
            modifier = Modifier
                .alpha(0.9f)
                .fillMaxWidth(),
            shape = RoundedCornerShape(MediumRoundedCornerShape1),
        ) {
            Column(
                modifier = Modifier
                    .padding(MediumPadding2),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(id = R.string.Are_you_sure),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = colorResource(id = R.color.gray_text),
                        fontWeight = FontWeight.Medium,
                        fontSize = SmallFontSize1
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(top = MediumPadding1)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(onClick = onClickYes) {
                        Text(
                            text = stringResource(id = R.string.Yes),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(id = R.color.white),
                                fontWeight = FontWeight.Bold,
                                fontSize = SmallFontSize1
                            )
                        )
                    }
                    Button(onClick = onClickNo) {
                        Text(
                            text = stringResource(id = R.string.No),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = colorResource(id = R.color.white),
                                fontWeight = FontWeight.Bold,
                                fontSize = SmallFontSize1
                            )
                        )
                    }
                }
            }
        }
    }
}