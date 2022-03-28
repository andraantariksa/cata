package id.shaderboi.cata.feature_todo.ui.home.todos.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import id.shaderboi.cata.feature_todo.ui.home.todos.view_model.ToDosEvent
import id.shaderboi.cata.feature_todo.ui.home.todos.view_model.ToDosViewModel

@Composable
fun TopBar(toDosViewModel: ToDosViewModel) {
    var isSearching by remember { mutableStateOf(false) }
    var searchButtonPosition by remember { mutableStateOf(Offset.Zero) }

    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Todo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            isSearching = true
                        },
                        modifier = Modifier.onGloballyPositioned {
                            val topAppBar = it.parentLayoutCoordinates!!.parentLayoutCoordinates!!
                            val coordinateInTopAppBar = topAppBar.localPositionOf(it, Offset.Zero)
                            val centerThisButton = Offset(
                                (coordinateInTopAppBar.x + it.size.width / 2) / topAppBar.size.width,
                                (coordinateInTopAppBar.y + it.size.height / 2) / topAppBar.size.height
                            )
                            searchButtonPosition = centerThisButton
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                    IconButton(
                        onClick = {
                            toDosViewModel.onEvent(ToDosEvent.ToggleSortToDoModal)
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                    }
                }
            }
            SearchBar(
                toDosViewModel,
                isSearching,
                onBackPressedCallback = {
                    isSearching = false
                },
                startCirclePosition = searchButtonPosition
            )
        }
    }
}

