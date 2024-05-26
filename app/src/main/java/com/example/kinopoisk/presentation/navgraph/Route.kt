package com.example.kinopoisk.presentation.navgraph

sealed class Route(
    val route: String
) {

    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object SearchScreen : Route(route = "searchScreen")
    data object BookmarkScreen : Route(route = "bookmarkScreen")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object KinopoiskNavigation : Route(route = "kinopoiskNavigation")
    data object KinopoiskNavigatorScreen : Route(route = "kinopoiskNavigator")
}