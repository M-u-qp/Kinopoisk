package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.common.normalizeTitleMovie

@Composable
fun SimilarMovieCard(
    modifier: Modifier = Modifier,
    item: SimilarItem,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .clickable { onClick?.invoke() }
    ) {
            //Постер фильма
            AsyncImage(
                modifier = modifier
                    .size(width = Dimens.MovieCardSizeWidth, height = Dimens.MovieCardSizeHeight)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(item.posterUrl).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        //Название фильма
        val nameMovie = normalizeTitleMovie(item.nameRu ?: item.nameEn ?: "")
        Text(
            modifier = modifier.padding(top = Dimens.ExtraSmallPadding2),
            text = nameMovie,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = Dimens.SmallFontSize1
            ),
            color = colorResource(id = R.color.black_text),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}