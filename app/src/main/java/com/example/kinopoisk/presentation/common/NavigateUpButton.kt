package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens

@Composable
fun NavigateUpButton(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit,
    text: String
) {

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {

        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_back_arrow
                ),
                contentDescription = null
            )
        }

        Text(
            modifier = Modifier.align(Alignment.Center),
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.MediumFontSize3,
                color = colorResource(id = R.color.black_text)
            )
        )
    }
}