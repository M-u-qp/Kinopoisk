package com.example.kinopoisk.presentation.details.details_staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding3
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.TitleCommon
import com.example.kinopoisk.presentation.details.details_staff.components.BestMovieCard
import com.example.kinopoisk.presentation.details.details_staff.components.DialogFilmography
import com.example.kinopoisk.presentation.details.details_staff.components.StaffInfoCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun StaffScreen(
    id: Int,
    state: StaffState,
    viewModel: StaffViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit,
    navigateUp: () -> Unit,
    navigateToAllMovies: (List<Movie?>) -> Unit
) {

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getStaffInfo(id)
        }
    }

    LaunchedEffect(key1 = state.isLoadingMovies) {
        withContext(Dispatchers.IO) {
            state.filterBestMovies.forEach { films ->
                films.filmId?.let { movieId ->
                    viewModel.getBestFilms(movieId)
                }
            }
            viewModel.updateIsLoadingMovies(false)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = Dimens.MediumPadding2)
            .statusBarsPadding()
    ) {
        IconButton(onClick = navigateUp) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_back_arrow
                ),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(SmallPadding1))

        state.staffInfo?.let {
            StaffInfoCard(staffInfo = it)
        }

        Spacer(modifier = Modifier.height(MediumPadding3))

        //Лучшие фильма актера
        val nameTitle = stringResource(id = R.string.The_best_films)
        val varParam = stringResource(id = R.string.All)

        TitleCommon(
            nameTitle = nameTitle,
            varParam = varParam,
            onClick = { navigateToAllMovies(state.listBestMovies.toList()) }
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            items(state.listBestMovies) { movie ->
                movie?.let {
                    BestMovieCard(
                        movie = movie,
                        onClick = { navigateToDetails(movie.kinopoiskId) }
                    )
                }
            }
        }

        //Фильмография
        val nameTitle2 = stringResource(id = R.string.Filmography)
        val varParam2 = stringResource(id = R.string.To_list)
        TitleCommon(
            modifier = Modifier.padding(top = MediumPadding3),
            nameTitle = nameTitle2,
            varParam = varParam2,
            onClick = { viewModel.updateVisibleDialogFilmography(true) }
        )

        Text(
            modifier = Modifier.padding(top = ExtraSmallPadding2),
            text = state.staffInfo?.films?.size.toString() + " фильма",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = SmallFontSize2,
                color = colorResource(id = R.color.gray_text)
            )
        )

        if (state.isShowDialogFilmography) {
            DialogFilmography(
                state = state,
                navigateToDetails = navigateToDetails
            )
        }
    }
}