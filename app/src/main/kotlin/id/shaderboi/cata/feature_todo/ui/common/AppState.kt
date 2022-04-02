package id.shaderboi.cata.feature_todo.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    navHostController: NavHostController = rememberNavController(),
    appScope: CoroutineScope = rememberCoroutineScope()
) = remember(navHostController, appScope) {
    AppState(
        navHostController = navHostController,
        appScope = appScope
    )
}

data class AppState(
    val navHostController: NavHostController,
    val appScope: CoroutineScope
)
