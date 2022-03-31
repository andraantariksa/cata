package id.shaderboi.cata.feature_todo.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState
import id.shaderboi.cata.feature_todo.ui.home.common.rememberHomeState
import id.shaderboi.cata.feature_todo.ui.home.components.BottomNavigationBar
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.create_todo.CreateToDoOverlay

@Composable
fun HomeScreen(appState: AppState) {
    val homeState = rememberHomeState()

    Box {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(homeState = homeState)
            },
        ) {
            HomeNavigation(appState = appState, homeState = homeState)
        }
        CreateToDoOverlay(homeViewModel = homeState.homeViewModel)
    }
}

@Preview
@Composable
internal fun AppScreenPreview() {
    HomeScreen(appState = rememberAppState())
}
