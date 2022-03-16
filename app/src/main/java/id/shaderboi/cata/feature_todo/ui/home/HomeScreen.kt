package id.shaderboi.cata.feature_todo.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.home.todos.components.create_todo.CreateToDoOverlay
import id.shaderboi.cata.feature_todo.ui.home.components.BottomNavigationBar

@Composable
fun HomeScreen(appState: AppState) {
    val homeState = rememberHomeState()
    val scaffoldState = rememberScaffoldState()

    Box {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(homeState = homeState)
            },
            scaffoldState = scaffoldState,
        ) {
            HomeNavigation(appState = appState, homeState = homeState)
        }
        CreateToDoOverlay(homeViewModel = homeState.homeViewModel)
    }
}