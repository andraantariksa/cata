package id.shaderboi.cata.feature_todo.ui.home.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import id.shaderboi.cata.feature_todo.ui.home.view_model.HomeViewModel

@Composable
fun rememberHomeState(
    navHostController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = hiltViewModel(),
) = remember(
    navHostController,
    homeViewModel
) {
    HomeState(
        navHostController = navHostController,
        homeViewModel = homeViewModel
    )
}


data class HomeState(
    val navHostController: NavHostController,
    val homeViewModel: HomeViewModel
)