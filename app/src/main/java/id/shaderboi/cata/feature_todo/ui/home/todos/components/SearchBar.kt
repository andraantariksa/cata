package id.shaderboi.cata.feature_todo.ui.home.todos.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.shaderboi.cata.feature_todo.ui.home.todos.view_model.ToDosEvent
import id.shaderboi.cata.feature_todo.ui.home.todos.view_model.ToDosViewModel

@Composable
fun SearchBar(
    toDosViewModel: ToDosViewModel,
    visible: Boolean,
    startCirclePosition: Offset = Offset(0.5f, 0.5f),
    onBackPressedCallback: () -> Unit
) {
    BackHandler(visible) {
        onBackPressedCallback()
        toDosViewModel.onEvent(ToDosEvent.OnSearchTextChange(""))
    }

    val circleRadius = animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween()
    )

    if (circleRadius.value > 0.0f) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .circularReveal(circleRadius, startCirclePosition)
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 20.dp)
        ) {
            IconButton(
                onClick = onBackPressedCallback,
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colors.onSurface
                )
            }
            TextField(
                value = toDosViewModel.toDos.value.searchQuery,
                onValueChange = { text ->
                    toDosViewModel.onEvent(ToDosEvent.OnSearchTextChange(text))
                },
                placeholder = {
                    Text("Search...")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSurface
                ),
            )
        }
    }
}