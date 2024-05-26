package com.example.kinopoisk.presentation.onboarding.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.MediumFontSize1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.onboarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
    page: Page
) {
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation
    val contentScale = if (orientation == Configuration.ORIENTATION_PORTRAIT) ContentScale.Crop else ContentScale.None

    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth(),
            painter = painterResource(id = page.image),
            contentDescription = null,
            contentScale = contentScale
        )
        Text(
            modifier = modifier.padding(
                start = MediumPadding2,
                top = MediumPadding2
            ),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = MediumFontSize1
            ),
            text = page.description,
            color = colorResource(id = R.color.onboarding_text_color)
        )
    }
}
