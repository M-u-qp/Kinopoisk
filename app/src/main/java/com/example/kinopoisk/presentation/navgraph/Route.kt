package com.example.kinopoisk.presentation.navgraph

sealed class Route(
    val route: String
) {

    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object SearchScreen : Route(route = "searchScreen")
    data object ProfileScreen : Route(route = "profileScreen")
    data object CollectionDBScreen : Route(route = "collectionDBScreen")
    data object AllStaffScreen : Route(route = "allStaffScreen")
    data object AllMovieScreen: Route(route = "allMoviesScreen")
    data object StaffScreen : Route(route = "staffScreen")
    data object DetailsScreen : Route(route = "detailsScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object KinopoiskNavigation : Route(route = "kinopoiskNavigation")
    data object KinopoiskNavigatorScreen : Route(route = "kinopoiskNavigator")
}