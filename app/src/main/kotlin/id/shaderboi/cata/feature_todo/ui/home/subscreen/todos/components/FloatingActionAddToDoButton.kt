package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import id.shaderboi.cata.R

@Composable
fun FloatingActionAddToDoButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = LocalContext.current.getString(R.string.add_to_do_fab),
            tint = MaterialTheme.colors.onPrimary
        )
    }
}

@Preview
@Composable
private fun FloatingActionAddToDoButtonPreview() {
    FloatingActionAddToDoButton {}
}
