package com.example.kinopoisk.presentation.search_filter.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.common.NavigateUpButton
import com.example.kinopoisk.presentation.search_filter.SearchFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(
    viewModel: SearchFilterViewModel
) {

    val showDialog = remember { mutableStateOf(false) }
    val datePickerFromState = rememberDatePickerState()
    val datePickerBeforeState = rememberDatePickerState()

    DatePickerDialog(onDismissRequest = {
        showDialog.value = false
        viewModel.updateVisibleDialogDatePicker(showDialog.value)
    },
        confirmButton = { },
        dismissButton = { }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            NavigateUpButton(
                navigateUp = {
                    viewModel.updateVisibleDialogDatePicker(false)
                },
                text = stringResource(id = R.string.Period)
            )

            DatePicker(state = datePickerFromState)

        }
    }
}