package id.shaderboi.cata.feature_todo.ui.todo.screen.view_model

import androidx.compose.material.SnackbarDuration

sealed class ToDoUIEvent {
    object CloseToDoScreen : ToDoUIEvent()
    object LockInput : ToDoUIEvent()
    object UnlockInput : ToDoUIEvent()
    class ShowSnackBar(
        val title: String,
        val actionLabel: String? = null,
        val duration: SnackbarDuration = SnackbarDuration.Short
    ) : ToDoUIEvent()
}