package com.example.kinopoisk.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.LargePadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding4
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.profile.components.CollectionCard

@Composable
fun ProfileScreen(
    state: ProfileState,
    viewModel: ProfileViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true) {
        viewModel.getBookmarkCollection("Хочу посмотреть")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = MediumPadding2, top = LargePadding2)
            .navigationBarsPadding()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(id = R.string.Viewed),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colorResource(id = R.color.black_text)
            )
            Row {
                Text(text = "10")
                Icon(
                    painter = painterResource(id = R.drawable.ic_open_list),
                    contentDescription = null
                )
            }
            LazyRow(
                modifier = Modifier
                    .padding(top = MediumPadding1)
            ) {

            }

            Spacer(modifier = Modifier.height(MediumPadding4))

            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.Collections),
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = colorResource(id = R.color.black_text)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SmallPadding1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_add), contentDescription = null)
                Text(
                    modifier = Modifier.padding(start = SmallPadding1),
                    text = stringResource(id = R.string.Create_collection),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = colorResource(id = R.color.black_text)
                )
            }
            LazyVerticalGrid(columns = GridCells.Fixed(2)) {
                items(state.allCollections.size) { item ->
//CollectionCard(icon =, nameCollection = , sizeCollection = )
                }
            }
        }
    }
}