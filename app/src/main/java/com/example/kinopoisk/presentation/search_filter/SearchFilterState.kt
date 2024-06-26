package com.example.kinopoisk.presentation.search_filter

data class SearchFilterState(
    var ratingPosition: ClosedFloatingPointRange<Float> = 1f..10f
)
