package com.example.kinopoisk.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.kinopoisk.presentation.movies_navigator.MoviesNavigator
import com.example.kinopoisk.presentation.onboarding.OnBoardingScreen
import com.example.kinopoisk.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewModel::onEvent)
            }
        }
        navigation(
            route = Route.KinopoiskNavigation.route,
            startDestination = Route.KinopoiskNavigatorScreen.route
        ) {
            composable(
                route = Route.KinopoiskNavigatorScreen.route
            ) {
                MoviesNavigator()
            }
        }
    }

}
