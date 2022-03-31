package id.shaderboi.cata.feature_todo.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.home.common.HomeState
import id.shaderboi.cata.feature_todo.ui.home.common.utils.HomeNavigationGraph
import id.shaderboi.cata.feature_todo.ui.home.subscreen.misc.MiscScreen
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.ToDosScreen

@Composable
fun HomeNavigation(appState: AppState, homeState: HomeState) {
    NavHost(
        navController = homeState.navHostController,
        startDestination = HomeNavigationGraph.ToDos.route
    ) {
        composable(HomeNavigationGraph.ToDos.route) {
            ToDosScreen(appState = appState, homeState = homeState)
        }
        composable(HomeNavigationGraph.Misc.route) {
            MiscScreen(appState = appState, homeState = homeState)
        }
    }
}