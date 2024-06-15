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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.CardCollectionSize
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding3
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding4
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding5
import com.example.kinopoisk.presentation.Dimens.IconSize
import com.example.kinopoisk.presentation.Dimens.SizeCollectionHeight
import com.example.kinopoisk.presentation.Dimens.SizeCollectionWidth
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2
import com.example.kinopoisk.presentation.common.TitleCollectionsDB

@Composable
fun CollectionCard(
    @DrawableRes icon: Int,
    nameCollection: String,
    sizeCollection: Int,
    onClickDelete: () -> Unit
) {

    val showIconDeleteCollection = TitleCollectionsDB.entries.any { it.value == nameCollection }
    val stateShowIconDeleteCollection = remember { mutableStateOf( showIconDeleteCollection) }

    Card(
        modifier = Modifier
            .size(CardCollectionSize),
        border = BorderStroke(color = colorResource(id = R.color.black), width = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (!stateShowIconDeleteCollection.value) {
                IconButton(
                    modifier = Modifier
                        .padding(top = ExtraSmallPadding5, end = ExtraSmallPadding5)
                        .align(Alignment.TopEnd),
                    onClick = onClickDelete) {
                    Icon(
                        modifier = Modifier.size(IconSize),
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(painter = painterResource(id = icon), contentDescription = null)
                Text(
                    modifier = Modifier.padding(top = ExtraSmallPadding3),
                    text = nameCollection,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = SmallFontSize2
                    ),
                    color = colorResource(id = R.color.black_text)
                )

                Box(
                    modifier = Modifier
                        .padding(top = ExtraSmallPadding4)
                        .size(width = SizeCollectionWidth, height = SizeCollectionHeight)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = ShapeDefaults.Medium
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = sizeCollection.toString(),
                        color = colorResource(id = R.color.white),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = SmallFontSize2,
                            fontWeight = FontWeight.Medium
                        ),
                    )
                }
            }
        }
    }
}