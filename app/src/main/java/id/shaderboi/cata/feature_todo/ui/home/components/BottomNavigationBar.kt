package id.shaderboi.cata.feature_todo.ui.home.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import id.shaderboi.cata.feature_todo.ui.home.common.HomeState
import id.shaderboi.cata.feature_todo.ui.home.common.rememberHomeState
import id.shaderboi.cata.feature_todo.ui.home.common.utils.HomeNavigationGraph

@Composable
fun BottomNavigationBar(homeState: HomeState) {
    BottomNavigation {
        val navBackStackEntry by homeState.navHostController.currentBackStackEntryAsState()
        BottomNavigationItem(
            selected = navBackStackEntry?.destination?.route == HomeNavigationGraph.ToDos.route,
            onClick = {
                homeState.navHostController.navigate(HomeNavigationGraph.ToDos.route)
            },
            icon = {
                Icon(imageVector = Icons.Default.ListAlt, contentDescription = "Todo")
            },
            label = { Text(text = "Todo") },
            unselectedContentColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.5F),
            selectedContentColor = MaterialTheme.colors.onPrimary
        )
        BottomNavigationItem(
            selected = navBackStackEntry?.destination?.route == HomeNavigationGraph.Misc.route,
            onClick = {
                homeState.navHostController.navigate(HomeNavigationGraph.Misc.route)
            },
            icon = {
                Icon(imageVector = Icons.Default.MoreHoriz, contentDescription = "Misc")
            },
            label = { Text(text = "Misc") },
            unselectedContentColor = MaterialTheme.colors.onPrimary.copy(alpha = 0.5F),
            selectedContentColor = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun BottomNavigationBarPreview() {
    BottomNavigationBar(homeState = rememberHomeState())
}
