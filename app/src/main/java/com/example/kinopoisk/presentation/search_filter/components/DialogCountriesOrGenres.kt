package com.example.kinopoisk.presentation.search_filter.components

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding1
import com.example.kinopoisk.presentation.Dimens.LargePadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.NavigateUpButton
import com.example.kinopoisk.presentation.search_filter.SearchFilterState
import com.example.kinopoisk.presentation.search_filter.SearchFilterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogCountriesOrGenres(
    state: SearchFilterState,
    viewModel: SearchFilterViewModel
) {

    val showDialog = remember { mutableStateOf(false) }
    val searchText = remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            showDialog.value = false
            viewModel.updateVisibleDialogCountriesOrGenres(showDialog.value)
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            when (state.selectedCountryOrGenre) {
                "Страна" -> {
                    NavigateUpButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MediumPadding2),
                        navigateUp = { viewModel.updateVisibleDialogCountriesOrGenres(false) },
                        text = stringResource(id = R.string.Country)
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MediumPadding2)
                            .padding(top = SmallPadding1)
                            .searchBarBorder(),
                        value = searchText.value,
                        onValueChange = { searchText.value = it },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                modifier = Modifier.size(Dimens.IconSize),
                                tint = colorResource(id = R.color.body)
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.Enter_name_country),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = SmallFontSize1
                                ),
                                color = colorResource(id = R.color.gray_text)
                            )
                        },
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.very_light_gray),
                            unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,

                    )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = LargePadding2),
                        verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding1)
                    ) {
                        state.listCountriesAndGenres?.let { countriesAndGenres ->
                            val filteredCountries = if (searchText.value.isNotEmpty()) {
                                countriesAndGenres.countries.filter { country ->
                                    country.country.startsWith(searchText.value, ignoreCase = true)
                                }
                            } else {
                                countriesAndGenres.countries
                            }
                            items(filteredCountries) { country ->
                                TextButton(
                                    modifier = Modifier.padding(horizontal = MediumPadding2),
                                    onClick = {
                                        viewModel.updateSelectedCountry(country)
                                        viewModel.updateVisibleDialogCountriesOrGenres(false)
                                    }
                                ) {
                                    Text(
                                        text = country.country,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = colorResource(id = R.color.black_text),
                                            fontSize = Dimens.MediumFontSize2,
                                        )
                                    )
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }

                "Жанр" -> {
                    NavigateUpButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MediumPadding2),
                        navigateUp = { viewModel.updateVisibleDialogCountriesOrGenres(false) },
                        text = stringResource(id = R.string.Genre)
                    )

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MediumPadding2)
                            .padding(top = SmallPadding1)
                            .searchBarBorder(),
                        value = searchText.value,
                        onValueChange = { searchText.value = it },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                modifier = Modifier.size(Dimens.IconSize),
                                tint = colorResource(id = R.color.body)
                            )
                        },
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.Enter_name_country),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = SmallFontSize1
                                ),
                                color = colorResource(id = R.color.gray_text)
                            )
                        },
                        shape = MaterialTheme.shapes.extraLarge,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = colorResource(id = R.color.very_light_gray),
                            unfocusedTextColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            cursorColor = if (isSystemInDarkTheme()) Color.White else Color.Black,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        textStyle = MaterialTheme.typography.bodySmall,
                        )

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MediumPadding2)
                            .padding(top = LargePadding2),
                        verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding1)
                    ) {
                        state.listCountriesAndGenres?.let { countriesAndGenres ->
                            val filteredGenres = if (searchText.value.isNotEmpty()) {
                                countriesAndGenres.genres.filter { genre ->
                                    genre.genre.startsWith(searchText.value, ignoreCase = true)
                                }
                            } else {
                                countriesAndGenres.genres
                            }
                            items(filteredGenres) { genre ->
                                TextButton(
                                    modifier = Modifier.padding(horizontal = MediumPadding2),
                                    onClick = {
                                        viewModel.updateSelectedGenre(genre)
                                        viewModel.updateVisibleDialogCountriesOrGenres(false)
                                    }
                                ) {
                                    Text(
                                        text = genre.genre,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = colorResource(id = R.color.black_text),
                                            fontSize = Dimens.MediumFontSize2,
                                        )
                                    )
                                }
                                HorizontalDivider()
                            }
                        }
                    }
                }

                else -> Unit
            }

        }
    }
}

private fun Modifier.searchBarBorder() = composed {
    if (!isSystemInDarkTheme()) {
        border(
            width = 1.dp,
            color = Color.Black,
            shape = MaterialTheme.shapes.extraLarge
        )
    } else {
        this
    }
}