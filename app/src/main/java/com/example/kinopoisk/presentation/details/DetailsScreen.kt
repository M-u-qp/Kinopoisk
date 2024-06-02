package com.example.kinopoisk.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding3
import com.example.kinopoisk.presentation.details.components.DetailsTopBar
import com.example.kinopoisk.presentation.details.components.MovieDetailsCard

@Composable
fun DetailsScreen(
    movie: Movie,
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        item {
            Box(modifier = Modifier) {
                MovieDetailsCard(movie = movie)
                DetailsTopBar(
                    onLikeClick = { event(DetailsEvent.LikeMovie) },
                    onBookmarkClick = { event(DetailsEvent.UpsertDeleteMovie(movie)) },
                    onShareClick = {
                        Intent(Intent.ACTION_SEND).also {
                            it.putExtra(Intent.EXTRA_TEXT, movie.webUrl)
                            it.type = "text/plain"
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    },
                    onBrowsingClick = {
                        Intent(Intent.ACTION_VIEW).also {
                            it.data = Uri.parse(movie.webUrl)
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    },
                    onBackClick = navigateUp
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = MediumPadding3)
                    .padding(horizontal = MediumPadding2)
            ) {
                Text(
                    text = movie.shortDescription ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = Dimens.MediumFontSize2
                    ),
                    color = colorResource(id = R.color.black_text)
                )
                Text(
                    text = movie.description ?: "",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = Dimens.MediumFontSize2
                    ),
                    color = colorResource(id = R.color.black_text),
                    maxLines = 5,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}