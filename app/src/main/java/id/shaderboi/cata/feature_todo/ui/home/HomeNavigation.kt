package id.shaderboi.cata.feature_todo.ui.home

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.home.misc.MiscScreen
import id.shaderboi.cata.feature_todo.ui.home.todos.ToDosScreen
import id.shaderboi.cata.feature_todo.ui.home.view_model.HomeState

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