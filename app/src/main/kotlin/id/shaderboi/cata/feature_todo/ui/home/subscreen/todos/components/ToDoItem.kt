package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState
import id.shaderboi.cata.feature_todo.ui.common.util.RootNavigationGraph
import id.shaderboi.cata.feature_todo.ui.home.common.HomeState
import id.shaderboi.cata.feature_todo.ui.home.common.rememberHomeState
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosEvent
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosViewModel

sealed class ToDoDismissAction(
    val imageVector: ImageVector,
    val contentIconDescription: String,
    val backgroundColor: Color,
) {
    object Delete : ToDoDismissAction(
        Icons.Default.Delete,
        "Delete",
        Color.Red
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToDoItem(
    appState: AppState,
    homeState: HomeState,
    toDo: ToDo,
    toDosViewModel: ToDosViewModel
) {
    val dismissState = rememberDismissState()

    val isDeleted = dismissState.isDismissed(DismissDirection.EndToStart)
    LaunchedEffect(isDeleted) {
        if (isDeleted) {
            toDosViewModel.onEvent(ToDosEvent.Delete(toDo))
        }
    }

    SwipeToDismiss(
        state = dismissState,
        background = {
            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss

            val action = when (direction) {
                DismissDirection.StartToEnd -> return@SwipeToDismiss
                DismissDirection.EndToStart -> ToDoDismissAction.Delete
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(action.backgroundColor)
                    .padding(horizontal = 10.dp)
            ) {
                Icon(
                    imageVector = action.imageVector,
                    contentDescription = action.contentIconDescription
                )
            }
        },
        directions = remember { setOf(DismissDirection.EndToStart) }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        appState.navHostController.navigate("${RootNavigationGraph.ToDoRootNavigationGraph.route}?id=${toDo.id}")
                    }
                ),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = toDo.checked,
                        onCheckedChange = {
                            toDosViewModel.onEvent(ToDosEvent.ToggleToDoCheck(toDo))
                        }
                    )
                    Text(
                        toDo.title,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = "Priority ${toDo.priority.name}",
                    tint = toDo.priority.color
                )
            }
        }
    }
}


@Preview
@Composable
private fun ToDoItemPreview() {
    ToDoItem(
        appState = rememberAppState(),
        homeState = rememberHomeState(),
        toDo = ToDo("This is a title", "This is a content", ToDoPriority.High),
        toDosViewModel = hiltViewModel()
    )
}
