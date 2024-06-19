package com.example.kinopoisk.presentation.details.details_staff

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.MediumPadding3
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.TitleCollection
import com.example.kinopoisk.presentation.common.TitleCommon
import com.example.kinopoisk.presentation.details.details_staff.components.StaffInfoCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun StaffScreen(
    id: Int,
    state: StaffState,
    viewModel: StaffViewModel = hiltViewModel(),
    navigateToDetails: (Int) -> Unit,
    navigateUp: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getStaffInfo(id)
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

        val nameTitle = stringResource(id = R.string.The_best_films)
        val varParam = stringResource(id = R.string.All)
        TitleCommon(
            nameTitle = nameTitle,
            varParam = varParam,
            onClick = {}
        )

        LazyRow {

        }


    }
}