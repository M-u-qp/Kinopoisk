package com.example.kinopoisk.presentation.all_staff

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.all_staff.components.ListStaffCard

@Composable
fun AllStaffScreen(
    listStaff: List<Staff>,
    navigateToStaffInfo: (Int) -> Unit,
    navigateUp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = MediumPadding2)
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

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
        ) {
            items(listStaff) { staff ->
                ListStaffCard(
                    staff = staff,
                    onClick = { staff.staffId?.let { navigateToStaffInfo(it) } }
                )
            }
        }
    }
}