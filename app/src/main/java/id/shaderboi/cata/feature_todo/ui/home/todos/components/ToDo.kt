package id.shaderboi.cata.feature_todo.ui.home.todos.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.RootNavigationGraph
import id.shaderboi.cata.feature_todo.ui.home.HomeState
import id.shaderboi.cata.feature_todo.ui.home.todos.ToDosViewModel

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
fun ToDo(
    appState: AppState,
    homeState: HomeState,
    toDo: ToDo,
    toDosViewModel: ToDosViewModel
) {
    val dismissState = rememberDismissState()
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
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp)
            ) {
                Checkbox(checked = false, onCheckedChange = {})
                Text(
                    toDo.title,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
            }
        }
    }
}