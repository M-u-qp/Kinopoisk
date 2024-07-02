package com.example.kinopoisk.presentation.movies_navigator

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.SeasonsItem
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.presentation.all_gallery.AllGalleryScreen
import com.example.kinopoisk.presentation.all_gallery.AllGalleryViewModel
import com.example.kinopoisk.presentation.all_movies.AllMoviesScreen
import com.example.kinopoisk.presentation.all_seasons.AllSeasonsScreen
import com.example.kinopoisk.presentation.all_staff.AllStaffScreen
import com.example.kinopoisk.presentation.collectiondb.CollectionDBScreen
import com.example.kinopoisk.presentation.collectiondb.CollectionDBViewModel
import com.example.kinopoisk.presentation.common.TypeGalleryRequest
import com.example.kinopoisk.presentation.details.details_movie.DetailsEvent
import com.example.kinopoisk.presentation.details.details_movie.DetailsScreen
import com.example.kinopoisk.presentation.details.details_movie.DetailsViewModel
import com.example.kinopoisk.presentation.details.details_staff.StaffScreen
import com.example.kinopoisk.presentation.details.details_staff.StaffViewModel
import com.example.kinopoisk.presentation.home.HomeScreen
import com.example.kinopoisk.presentation.home.HomeViewModel
import com.example.kinopoisk.presentation.movies_navigator.components.BottomNavigationItem
import com.example.kinopoisk.presentation.movies_navigator.components.MoviesBottomNavigation
import com.example.kinopoisk.presentation.navgraph.Route
import com.example.kinopoisk.presentation.profile.ProfileScreen
import com.example.kinopoisk.presentation.profile.ProfileViewModel
import com.example.kinopoisk.presentation.search.SearchScreen
import com.example.kinopoisk.presentation.search.SearchViewModel
import com.example.kinopoisk.presentation.search_filter.FilterData
import com.example.kinopoisk.presentation.search_filter.SearchFilterScreen
import com.example.kinopoisk.presentation.search_filter.SearchFilterViewModel

@Composable
fun MoviesNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home_nav, text = "Дом"),
            BottomNavigationItem(icon = R.drawable.ic_search_nav, text = "Поиск"),
            BottomNavigationItem(icon = R.drawable.ic_profile_nav, text = "Профиль")
        )
    }

    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.SearchScreen.route -> 1
            Route.ProfileScreen.route -> 2
            else -> 0
        }
    }


    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.SearchScreen.route ||
                backstackState?.destination?.route == Route.ProfileScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                MoviesBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.SearchScreen.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.ProfileScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val movies = viewModel.topPopularAll.collectAsLazyPagingItems()
                HomeScreen(
                    viewModel = viewModel,
                    movies = movies,
                    navigateToSearch = {
                        navigateToTab(
                            navController = navController,
                            route = Route.SearchScreen.route
                        )
                    },
                    navigateToDetails = { movieId ->
                        navigateToDetails(
                            navController = navController,
                            movieId = movieId
                        )
                    },
                    navigateToAllMovies = { listMovies ->
                        navigateToAll(
                            navController = navController,
                            listAll = listMovies,
                            type = "collection"
                        )
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val state = viewModel.state.value
                if (navController.previousBackStackEntry?.destination?.route == Route.SearchFilterScreen.route) {
                    navController.previousBackStackEntry?.savedStateHandle?.get<FilterData>("filterData")
                        ?.let { filterData ->
                            SearchScreen(
                                state = state,
                                event = viewModel::onEvent,
                                navigateToDetails = { movieId ->
                                    navigateToDetails(
                                        navController = navController,
                                        movieId = movieId
                                    )
                                },
                                navigateToSearchFilter = { navController.navigate(route = Route.SearchFilterScreen.route) },
                                filterData = listOf(filterData)
                            )
                        }
                }else {
                    SearchScreen(
                        state = state,
                        event = viewModel::onEvent,
                        navigateToDetails = { movieId ->
                            navigateToDetails(
                                navController = navController,
                                movieId = movieId
                            )
                        },
                        navigateToSearchFilter = { navController.navigate(route = Route.SearchFilterScreen.route) },
                        filterData = emptyList()
                    )
                }
            }
            composable(route = Route.SearchFilterScreen.route) {
                val viewModel: SearchFilterViewModel = hiltViewModel()
                val state = viewModel.state.value
                SearchFilterScreen(
                    viewModel = viewModel,
                    navigateUp = { navController.navigateUp() },
                    state = state,
                    navigateToSearch = { filterData ->
                        navigateToSearch(
                            navController = navController,
                            filterData = filterData
                        )
                    }
                )
            }
            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("movieId")
                    ?.let { movieId ->

                        viewModel.getGalleryMovie(
                            id = movieId,
                            type = TypeGalleryRequest.STILL.name
                        )
                        val galleryMovieStill =
                            viewModel.state.value.galleryItem.collectAsLazyPagingItems()

                        DetailsScreen(
                            movieId = movieId,
                            viewModel = viewModel,
                            event = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() },
                            navigateToStaffInfo = { id ->
                                navigateToStaffInfo(
                                    navController = navController,
                                    id = id
                                )
                            },
                            navigateToAllStaff = { listStaff ->
                                navigateToAll(
                                    navController = navController,
                                    listAll = listStaff,
                                    type = "staff"
                                )
                            },
                            navigateToAllMovies = { listMovies ->
                                navigateToAll(
                                    navController = navController,
                                    listAll = listMovies,
                                    type = "similar"
                                )
                            },
                            galleryMovieStill = galleryMovieStill,
                            navigateToDetails = { similarMovieId ->
                                navigateToDetails(
                                    navController = navController,
                                    movieId = similarMovieId
                                )
                            },
                            navigateToAllGallery = { movieIdGallery ->
                                navigateToAllGallery(
                                    navController = navController,
                                    movieId = movieIdGallery
                                )
                            },
                            navigateToAllSeasons = { seasonsItems->
                                navigateToAll(
                                    navController = navController,
                                    listAll = seasonsItems,
                                    type = "seasons"
                                )
                            }
                        )
                    }
            }
            composable(route = Route.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                val state = viewModel.state.value
                ProfileScreen(
                    state = state,
                    viewModel = viewModel,
                    navigateToDetails = { movieId ->
                        navigateToDetails(
                            navController = navController,
                            movieId = movieId
                        )
                    },
                    navigateToCollection = { nameCollection ->
                        navigateToCollectionDB(
                            navController = navController,
                            nameCollection = nameCollection
                        )
                    }
                )
            }
            composable(route = Route.CollectionDBScreen.route) {
                val viewModel: CollectionDBViewModel = hiltViewModel()
                val state = viewModel.state.value
                navController.previousBackStackEntry?.savedStateHandle?.get<String>("nameCollection")
                    ?.let { nameCollection ->
                        CollectionDBScreen(
                            state = state,
                            viewModel = viewModel,
                            nameCollection = nameCollection,
                            navigateToDetails = { movieId ->
                                navigateToDetails(
                                    navController = navController,
                                    movieId = movieId
                                )
                            },
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }
            composable(route = Route.StaffScreen.route) {
                val viewModel: StaffViewModel = hiltViewModel()
                val state = viewModel.state.value
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>("id")
                    ?.let { id ->
                        StaffScreen(
                            id = id,
                            state = state,
                            viewModel = viewModel,
                            navigateToDetails = { movieId ->
                                navigateToDetails(
                                    navController = navController,
                                    movieId = movieId
                                )
                            },
                            navigateUp = { navController.navigateUp() },
                            navigateToAllMovies = { listMovies ->
                                navigateToAll(
                                    navController = navController,
                                    listAll = listMovies,
                                    type = "bestMovies"
                                )

                            }
                        )
                    }
            }

            composable(route = Route.AllStaffScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<List<Staff>>("listStaff")
                    ?.let { listStaff ->
                        AllStaffScreen(
                            listStaff = listStaff,
                            navigateToStaffInfo = { id ->
                                navigateToStaffInfo(
                                    navController = navController,
                                    id = id
                                )
                            },
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }
            composable(route = Route.AllMovieScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<List<SimilarItem>>("listAll")
                    ?.let { listMovies ->
                        AllMoviesScreen(
                            movies = listMovies,
                            navigateToDetails = { movieId ->
                                navigateToDetails(
                                    navController = navController,
                                    movieId = movieId
                                )
                            },
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }
            composable(route = Route.AllGalleryScreen.route) {
                val viewModel: AllGalleryViewModel = hiltViewModel()
                val state = viewModel.state.value
                navController.previousBackStackEntry?.savedStateHandle?.get<Int>(
                    "galleryAll"
                )
                    ?.let { movieId ->
                        AllGalleryScreen(
                            state = state,
                            movieId = movieId,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }
            composable(route = Route.AllSeasonsScreen.route) {
                navController.previousBackStackEntry?.savedStateHandle?.get<List<SeasonsItem>>("seasonsItem")
                    ?.let { seasonsItems ->
                        AllSeasonsScreen(
                            serialName = "",
                            seasonsItem = seasonsItems,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, movieId: Int) {
    navController.currentBackStackEntry?.savedStateHandle?.set("movieId", movieId)
    navController.navigate(route = Route.DetailsScreen.route)
}

private fun navigateToStaffInfo(navController: NavController, id: Int) {
    navController.currentBackStackEntry?.savedStateHandle?.set("id", id)
    navController.navigate(route = Route.StaffScreen.route)
}

private fun navigateToCollectionDB(navController: NavController, nameCollection: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("nameCollection", nameCollection)
    navController.navigate(route = Route.CollectionDBScreen.route)
}

private fun navigateToAll(navController: NavController, listAll: List<*>, type: String) {
    navController.currentBackStackEntry?.savedStateHandle?.set("listAll", listAll)
    when (type) {
        "staff" -> {
            navController.navigate(route = Route.AllStaffScreen.route)
        }
        "seasons" -> {
            navController.navigate(route = Route.AllSeasonsScreen.route)
        }
        else -> {
            navController.navigate(route = Route.AllMovieScreen.route)
        }
    }
}

private fun navigateToSearch(navController: NavController, filterData: List<FilterData>) {
    navController.currentBackStackEntry?.savedStateHandle?.set("filterData", filterData)
    navController.navigate(route = Route.SearchScreen.route)
}

private fun navigateToAllGallery(
    navController: NavController,
    movieId: Int
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("galleryAll", movieId)
    navController.navigate(route = Route.AllGalleryScreen.route)
}
