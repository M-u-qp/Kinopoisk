package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens

@Composable
fun TitleCollection(
    nameCollection: String,
    onClick: () -> Unit
    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = Dimens.LargePadding1, end = Dimens.MediumPadding2),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = Dimens.MediumFontSize3
            ),
            text = nameCollection,
            color = colorResource(id = R.color.black_text)
        )
        Text(
            modifier = Modifier.clickable { onClick() },
            text = stringResource(id = R.string.All),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = Dimens.SmallFontSize1
            ),
            color = MaterialTheme.colorScheme.primary
        )
    }
}