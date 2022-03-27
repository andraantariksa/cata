package id.shaderboi.cata.feature_todo.ui.home.todos.view_model

import androidx.compose.material.SnackbarDuration

sealed class ToDosUIEvent {
    class Snackbar(
        val message: String,
        val actionLabel: String?,
        val duration: SnackbarDuration,
        val onDissmissed: (() -> Unit)? = null,
        val onActionPerformed: (() -> Unit)? = null,
    ) : ToDosUIEvent()
}