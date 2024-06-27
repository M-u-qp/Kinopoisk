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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.NavigateUpButton
import com.example.kinopoisk.presentation.common.ToggleButton
import com.example.kinopoisk.presentation.search_filter.components.RatingSlider

@Composable
fun SearchFilterScreen(
    viewModel: SearchFilterViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {

    val selectedButtonType = remember { mutableIntStateOf(0) }
    val selectedButtonSort = remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
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
                isSelected = selectedButtonType.intValue == 0,
                onToggle = { selectedButtonType.intValue = 0 },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(LargeCornerSize),
                    bottomStart = CornerSize(LargeCornerSize),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Movies),
                isSelected = selectedButtonType.intValue == 1,
                onToggle = { selectedButtonType.intValue = 1 },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Serials),
                isSelected = selectedButtonType.intValue == 2,
                onToggle = { selectedButtonType.intValue = 2 },
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

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Россия",
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

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Комедия",
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

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "с 1990 до 2024",
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
            Text(
                text = "с ${viewModel.state.value.ratingPosition.start.toInt()} до ${viewModel.state.value.ratingPosition.endInclusive.toInt()}",
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
                isSelected = selectedButtonSort.intValue == 0,
                onToggle = { selectedButtonSort.intValue = 0 },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(LargeCornerSize),
                    bottomStart = CornerSize(LargeCornerSize),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Popular),
                isSelected = selectedButtonSort.intValue == 1,
                onToggle = { selectedButtonSort.intValue = 1 },
                shape = MaterialTheme.shapes.medium.copy(
                    topStart = CornerSize(0.dp),
                    bottomStart = CornerSize(0.dp),
                    topEnd = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                )
            )
            ToggleButton(
                text = stringResource(id = R.string.Rating),
                isSelected = selectedButtonSort.intValue == 2,
                onToggle = { selectedButtonSort.intValue = 2 },
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
    }
}