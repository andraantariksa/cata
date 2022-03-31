package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add TODO",
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun FloatingActionButtonPreview() {
    FloatingActionButton {}
}
