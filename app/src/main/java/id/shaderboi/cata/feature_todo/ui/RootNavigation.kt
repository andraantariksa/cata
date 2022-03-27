package id.shaderboi.cata.feature_todo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import id.shaderboi.cata.feature_todo.ui.home.HomeScreen
import id.shaderboi.cata.feature_todo.ui.todo.screen.ToDoScreen

@Composable
fun RootNavigation(appState: AppState) {
    NavHost(
        navController = appState.navHostController,
        startDestination = RootNavigationGraph.HomeRootNavigationGraph.route,
    ) {
        composable(route = RootNavigationGraph.HomeRootNavigationGraph.route) {
            HomeScreen(
                appState = appState
            )
        }
        composable(
            route = "${RootNavigationGraph.ToDoRootNavigationGraph.route}?id={id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt("id")!!
            ToDoScreen(
                appState = appState,
                toDoId = id,
            )
        }
    }
}