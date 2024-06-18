package com.example.kinopoisk.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding4
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.Dimens.StaffCardInHomeHeight
import com.example.kinopoisk.presentation.Dimens.StaffCardInHomeWidth

@Composable
fun StaffCard(
    staff: Staff
) {
    val context = LocalContext.current


    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = StaffCardInHomeWidth, height = StaffCardInHomeHeight)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(staff.posterUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(start = SmallPadding1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = staff.nameRu ?: staff.nameEn ?: "",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = colorResource(id = R.color.black_text),
                    fontSize = SmallFontSize1
                )
            )
            Text(
                modifier = Modifier.padding(top = ExtraSmallPadding4),
                text = staff.description ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = SmallFontSize2
                )
            )
        }
    }
}
