package id.shaderboi.cata.feature_todo.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState

@Composable
fun AppScreen() {
    Surface(
        color = MaterialTheme.colors.background
    ) {
        val appState = rememberAppState()
        RootNavigation(appState = appState)
    }
}

@Preview
@Composable
private fun AppScreenPreview() {
    AppScreen()
}
