package com.example.kinopoisk.presentation.all_staff.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.presentation.Dimens

@Composable
fun ListStaffCard(
    staff: Staff,
    onClick: () -> Unit
) {

    val context = LocalContext.current


    Row(
        modifier = Modifier.clickable { onClick() }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = Dimens.StaffCardWidth, height = Dimens.StaffCardHeight)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(staff.posterUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(start = Dimens.SmallPadding1)
        ) {
            Text(
                text = staff.nameRu ?: staff.nameEn ?: "",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black_text),
                    fontSize = Dimens.MediumFontSize2
                )
            )
            Text(
                modifier = Modifier.padding(top = Dimens.ExtraSmallPadding4),
                text = staff.description ?: "",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = Dimens.SmallFontSize2
                )
            )
        }
    }
}