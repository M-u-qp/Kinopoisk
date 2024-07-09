package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.MediumFontSize3
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1

@Composable
fun TitleCommon(
    modifier: Modifier = Modifier,
    nameTitle: String,
    varParam: String,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            style = MaterialTheme.typography.displaySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = MediumFontSize3
            ),
            text = nameTitle,
            color = colorResource(id = R.color.black_text)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = varParam,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = SmallFontSize1,
                    fontWeight = FontWeight.Medium
                ),
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = onClick) {
                Icon(
                    modifier = Modifier.scale(1.5f),
                    painter = painterResource(id = R.drawable.ic_open_list),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
