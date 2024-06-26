package com.example.kinopoisk.presentation.search_filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.LargeCornerSize
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.NavigateUpButton
import com.example.kinopoisk.presentation.common.SortSearchFilter
import com.example.kinopoisk.presentation.common.ToggleButton
import com.example.kinopoisk.presentation.common.TypeSearchFilter
import com.example.kinopoisk.presentation.search_filter.components.DialogCountriesOrGenres
import com.example.kinopoisk.presentation.search_filter.components.DialogDatePicker
import com.example.kinopoisk.presentation.search_filter.components.RatingSlider

@Composable
fun SearchFilterScreen(
    viewModel: SearchFilterViewModel = hiltViewModel(),
    state: SearchFilterState,
    navigateUp: () -> Unit,
    navigateToSearch: (List<FilterData>) -> Unit
) {

    val selectedButtonType = remember { mutableStateOf(state.typeSearchFilter) }
    val selectedButtonSort = remember { mutableStateOf(state.sortSearchFilter) }
    val scrollState = rememberScrollState()

    if (state.showDialogCountriesOrGenres) {
        DialogCountriesOrGenres(
            state = state,
            viewModel = viewModel
        )
    }

    if (state.showDialogDatePicker) {
        DialogDatePicker(viewModel = viewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(state = scrollState)
    ) {

        NavigateUpButton(
            modifier = Modifier.padding(start = MediumPadding2),
            navigateUp = navigateUp,
            text = stringResource(id = R.string.Search_settings)
        )

        Spacer(modifier = Modifier.height(SmallPadding1))

        Text(
            modifier = Modifier.padding(start = MediumPadding2),
            text = stringResource(id = R.string.Show),
            style = MaterialTheme.typography.bodySmall.copy(
                color = colorResource(id = R.color.gray_text),
                fontSize = Dimens.SmallFontSize2,
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .padding(top = SmallPadding1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ToggleButton(
                text = stringResource(id = R.string.All),
                isSelected = selectedButtonType.value == TypeSearchFilter.ALL,
                onToggle = { selectedButtonType.value = TypeSearchFilter.ALL },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(LargeCornerSize),
                    bottomStart = CornerSize(LargeCornerSize),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Movies),
                isSelected = selectedButtonType.value == TypeSearchFilter.FILM,
                onToggle = { selectedButtonType.value = TypeSearchFilter.FILM },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Serials),
                isSelected = selectedButtonType.value == TypeSearchFilter.TV_SERIES,
                onToggle = { selectedButtonType.value = TypeSearchFilter.TV_SERIES },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    topEnd = CornerSize(LargeCornerSize),
                    bottomEnd = CornerSize(LargeCornerSize)
                )
            )
        }

        Spacer(modifier = Modifier.height(SmallPadding1))

        //Страна
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.Country),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.black_text),
                    fontSize = Dimens.MediumFontSize2,
                )
            )

            TextButton(onClick = {
                viewModel.updateVisibleDialogCountriesOrGenres(true)
                viewModel.updateSelectedCountryOrGenre("Страна")
            }) {
                Text(
                    text = state.selectedCountry.country,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colorResource(id = R.color.gray_text),
                        fontSize = Dimens.SmallFontSize1,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(ExtraSmallPadding2))

        //Жанр
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.Genre),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.black_text),
                    fontSize = Dimens.MediumFontSize2,
                )
            )

            TextButton(onClick = {
                viewModel.updateVisibleDialogCountriesOrGenres(true)
                viewModel.updateSelectedCountryOrGenre("Жанр")
            }) {
                Text(
                    text = state.selectedGenre.genre,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colorResource(id = R.color.gray_text),
                        fontSize = Dimens.SmallFontSize1,
                    )
                )

            }
        }

        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(ExtraSmallPadding2))

        //Год
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.Year),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.black_text),
                    fontSize = Dimens.MediumFontSize2,
                )
            )

            TextButton(onClick = {
                viewModel.updateVisibleDialogDatePicker(true)
            }) {
                Text(
                    text = "с ${state.yearsPosition.first} до ${state.yearsPosition.last}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = colorResource(id = R.color.gray_text),
                        fontSize = Dimens.SmallFontSize1,
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(SmallPadding1))

        //Рейтинг

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.Rating),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.black_text),
                    fontSize = Dimens.MediumFontSize2,
                )
            )
            val textRating =
                if (state.ratingPosition.start.toInt() == 1 && state.ratingPosition.endInclusive.toInt() == 10) {
                    stringResource(id = R.string.any)
                } else {
                    "с ${state.ratingPosition.start.toInt()} до ${state.ratingPosition.endInclusive.toInt()}"
                }
            Text(
                text = textRating,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = Dimens.SmallFontSize1,
                )
            )
        }
        RatingSlider(
            modifier = Modifier.padding(horizontal = MediumPadding2),
            viewModel = viewModel
        )

        Spacer(modifier = Modifier.height(ExtraSmallPadding2))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(SmallPadding1))

        Text(
            modifier = Modifier.padding(start = MediumPadding2),
            text = stringResource(id = R.string.Sort),
            style = MaterialTheme.typography.bodySmall.copy(
                color = colorResource(id = R.color.gray_text),
                fontSize = Dimens.SmallFontSize2,
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .padding(top = SmallPadding1),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ToggleButton(
                text = stringResource(id = R.string.Date),
                isSelected = selectedButtonSort.value == SortSearchFilter.YEAR,
                onToggle = { selectedButtonSort.value = SortSearchFilter.YEAR },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(LargeCornerSize),
                    bottomStart = CornerSize(LargeCornerSize),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Popular),
                isSelected = selectedButtonSort.value == SortSearchFilter.NUM_VOTE,
                onToggle = { selectedButtonSort.value = SortSearchFilter.NUM_VOTE },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Rating),
                isSelected = selectedButtonSort.value == SortSearchFilter.RATING,
                onToggle = { selectedButtonSort.value = SortSearchFilter.RATING },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    topEnd = CornerSize(LargeCornerSize),
                    bottomEnd = CornerSize(LargeCornerSize)
                )
            )
        }

        Spacer(modifier = Modifier.height(SmallPadding1))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(MediumPadding1))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navigateToSearch(listOf( FilterData()))
            }
        ) {
            Text(
                text = stringResource(id = R.string.Search),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.white),
                    fontSize = Dimens.MediumFontSize2,
                )
            )
        }
    }
}

data class FilterData(
    val ratingFrom: Int = 1,
    val ratingTo: Int = 10,
    val selectedCountry: List<Int> = listOf(1),
    val selectedGenre: List<Int> = listOf(1),
    var yearsFrom: Int = 1900,
    val yearsTo: Int = 2024,
    val typeSearchFilter: String = "FILM",
    val sortSearchFilter: String = "RATING",
    )