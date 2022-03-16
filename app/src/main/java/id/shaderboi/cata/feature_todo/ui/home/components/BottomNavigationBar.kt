package id.shaderboi.cata.feature_todo.ui.home.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import id.shaderboi.cata.R
import id.shaderboi.cata.feature_todo.ui.home.HomeNavigationGraph
import id.shaderboi.cata.feature_todo.ui.home.HomeState
import id.shaderboi.cata.feature_todo.ui.home.homeNavigations

@Composable
fun BottomNavigationBar(homeState: HomeState) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary
    ) {
        BottomNavigationItem(
            selected = false,
            onClick = {
                homeState.navHostController.navigate(HomeNavigationGraph.ToDos.route)
            },
            icon = {
                Icon(imageVector = Icons.Default.ListAlt, contentDescription = "Todo")
            },
            label = { Text(text = "Todo") },
            unselectedContentColor = MaterialTheme.colors.secondary,
            selectedContentColor = MaterialTheme.colors.primary
        )
        BottomNavigationItem(
            selected = false,
            onClick = {
                homeState.navHostController.navigate(HomeNavigationGraph.Misc.route)
            },
            icon = {
                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = "Misc")
            },
            label = { Text(text = "Misc") },
            unselectedContentColor = MaterialTheme.colors.secondary,
            selectedContentColor = MaterialTheme.colors.primary
        )
    }
}