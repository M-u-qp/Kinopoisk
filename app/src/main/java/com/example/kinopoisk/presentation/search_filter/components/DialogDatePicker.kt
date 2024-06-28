package com.example.kinopoisk.presentation.search_filter.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.NavigateUpButton
import com.example.kinopoisk.presentation.common.ToggleButton
import com.example.kinopoisk.presentation.common.listLast100Years
import com.example.kinopoisk.presentation.search_filter.SearchFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogDatePicker(
    viewModel: SearchFilterViewModel
) {

    val showDialog = remember { mutableStateOf(false) }
    val cardScrollState = rememberScrollState()
    val selectedYearFrom = remember { mutableIntStateOf(viewModel.state.value.yearsPosition.first) }
    val selectedYearBefore =
        remember { mutableIntStateOf(viewModel.state.value.yearsPosition.last) }

    Dialog(
        onDismissRequest = {
            showDialog.value = false
            viewModel.updateVisibleDialogDatePicker(showDialog.value)
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = SmallPadding1)
                .verticalScroll(state = cardScrollState)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = DatePickerDefaults.shape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                NavigateUpButton(
                    navigateUp = {
                        viewModel.updateVisibleDialogDatePicker(false)
                    },
                    text = stringResource(id = R.string.Period)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = SmallPadding1)
                ) {
                    //Дата от
                    Text(
                        modifier = Modifier.padding(vertical = SmallPadding1),
                        text = stringResource(id = R.string.Search_in_period_from),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colorResource(id = R.color.gray_text),
                            fontSize = Dimens.SmallFontSize1,
                        )
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(224.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(SmallPadding1),
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding1),
                            horizontalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding1)
                        ) {
                            items(count = listLast100Years().size) { index ->
                                ToggleButton(
                                    text = listLast100Years()[index].toString(),
                                    isSelected = selectedYearFrom.intValue == listLast100Years()[index],
                                    onToggle = {
                                        selectedYearFrom.intValue = listLast100Years()[index]
                                        if (selectedYearFrom.intValue > selectedYearBefore.intValue) {
                                            selectedYearFrom.intValue = selectedYearBefore.intValue
                                        }
                                    }
                                )
                            }
                        }
                    }
                    //Дата до
                    Text(
                        modifier = Modifier.padding(vertical = SmallPadding1),
                        text = stringResource(id = R.string.Search_in_period_before),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = colorResource(id = R.color.gray_text),
                            fontSize = Dimens.SmallFontSize1,
                        )
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(224.dp),
                        border = BorderStroke(1.dp, Color.Black),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        )
                    ) {

                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(SmallPadding1),
                            columns = GridCells.Fixed(3),
                            verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding1),
                            horizontalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding1)
                        ) {
                            items(count = listLast100Years().size) { index ->
                                ToggleButton(
                                    text = listLast100Years()[index].toString(),
                                    isSelected = selectedYearBefore.intValue == listLast100Years()[index],
                                    onToggle = {
                                        selectedYearBefore.intValue = listLast100Years()[index]
                                        if (selectedYearBefore.intValue < selectedYearFrom.intValue) {
                                            selectedYearBefore.intValue = selectedYearFrom.intValue
                                        }
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(SmallPadding1))
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        viewModel.updateYearsPosition(selectedYearFrom.intValue..selectedYearBefore.intValue)
                        viewModel.updateVisibleDialogDatePicker(false)
                    }) {
                        Text(text = stringResource(id = R.string.Choose),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = Dimens.MediumFontSize2,
                            ))
                    }

                    TextButton(
                        modifier = Modifier,
                        border = BorderStroke(1.dp, Color.Black),
                        onClick = { viewModel.updateVisibleDialogDatePicker(false) }) {
                        Text(text = stringResource(id = R.string.Cancellation),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = Dimens.SmallFontSize1,
                            ))
                    }
                }
                Spacer(modifier = Modifier.height(SmallPadding1))
            }
        }
    }
}