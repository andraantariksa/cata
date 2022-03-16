package id.shaderboi.cata.feature_todo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable

@Composable
fun AppScreen() {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        val appState = rememberAppState()
        RootNavigation(appState = appState)
    }
}