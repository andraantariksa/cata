package id.shaderboi.cata.feature_todo.ui.todo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import id.shaderboi.cata.feature_todo.domain.util.Resource
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.todo.view_model.ToDoEvent
import id.shaderboi.cata.feature_todo.ui.todo.view_model.ToDoViewModel

@Composable
fun ToDoBottomAppBar(
    appState: AppState,
    toDoViewModel: ToDoViewModel,
    onPressPriorityButton: () -> Unit,
    onBack: (() -> Unit)? = null,
) {
    BottomAppBar(
        navigationIcon = {
            if (onBack != null && appState.navHostController.previousBackStackEntry != null) {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    ) {
        when (val state = toDoViewModel.toDoState) {
            is Resource.Loaded -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onPressPriorityButton
                    ) {
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = "Select priority",
                            tint = state.data.priority.color,
                        )
                    }

                    TextButton(
                        onClick = {
                            toDoViewModel.onEvent(ToDoEvent.InvertCheck)
                            toDoViewModel.onEvent(ToDoEvent.Save(appState.appScope))
                            appState.navHostController.popBackStack()
                        }
                    ) {
                        Text(
                            if (state.data.checked)
                                "Mark as incomplete"
                            else
                                "Mark as complete",
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 15.sp
                        )
                    }
                }
            }
            else -> {}
        }
    }
}