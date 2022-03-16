package id.shaderboi.cata.feature_todo.ui.home.todos.components

import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun TopBar() {
    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
        actions = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
        },
        title = {}
    )
}