package com.example.kinopoisk.presentation.profile.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.CardCollectionSize
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding4
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2

@Composable
fun CollectionCard(
    @DrawableRes icon: Int,
    nameCollection: String,
    sizeCollection: Int
) {

    Card(
        modifier = Modifier.size(CardCollectionSize),
        border = BorderStroke(color = colorResource(id = R.color.black), width = 1.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = null)
            Text(
                modifier = Modifier.padding(top = ExtraSmallPadding4),
                text = nameCollection,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = SmallFontSize2
                ),
                color = colorResource(id = R.color.black_text)
            )
            Box(
                modifier = Modifier.padding(top = ExtraSmallPadding4)
//                .size(Dimens.RatingMovieWidth, Dimens.RatingMovieHeight)
                    .padding(Dimens.ExtraSmallPadding1)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = ShapeDefaults.ExtraSmall
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = sizeCollection.toString(),
                    color = colorResource(id = R.color.white),
                    fontSize = Dimens.ExtraSmallFontSize1,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}