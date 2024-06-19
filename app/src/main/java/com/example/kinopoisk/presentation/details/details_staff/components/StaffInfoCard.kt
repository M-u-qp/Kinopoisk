package com.example.kinopoisk.presentation.details.details_staff.components

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
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.StaffInfo
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding4
import com.example.kinopoisk.presentation.Dimens.MediumFontSize2
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.Dimens.StaffCardHeight
import com.example.kinopoisk.presentation.Dimens.StaffCardWidth

@Composable
fun StaffInfoCard(
    staffInfo: StaffInfo
) {

    val context = LocalContext.current


    Row(
        modifier = Modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = StaffCardWidth, height = StaffCardHeight)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(staffInfo.posterUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(start = SmallPadding1)
        ) {
            Text(
                text = staffInfo.nameRu ?: staffInfo.nameEn ?: "",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black_text),
                    fontSize = MediumFontSize2
                )
            )
            Text(
                modifier = Modifier.padding(top = ExtraSmallPadding4),
                text = staffInfo.profession ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = SmallFontSize2
                )
            )
        }
    }
}