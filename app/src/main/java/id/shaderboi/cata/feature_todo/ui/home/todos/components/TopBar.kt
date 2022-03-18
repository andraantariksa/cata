package id.shaderboi.cata.feature_todo.ui.home.todos.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun TopBar() {
    var isSearching by remember { mutableStateOf(false) }
    var searchButtonPosition by remember { mutableStateOf(Offset.Zero) }

    TopAppBar(
        backgroundColor = MaterialTheme.colors.primary,
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
                    modifier = Modifier.padding(start = 10.dp)
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

                        }
                    ) {
                        Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort")
                    }
                }
            }
            SearchBar(
                isSearching,
                onBackPressedCallback = {
                    isSearching = false
                },
                startCirclePosition = searchButtonPosition
            )
        }
    }
}

@Composable
fun SearchBar(
    visible: Boolean,
    startCirclePosition: Offset = Offset(0.5f, 0.5f),
    onBackPressedCallback: () -> Unit
) {
    BackHandler(visible) {
        onBackPressedCallback()
    }

    var searchText by remember { mutableStateOf("") }
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
                value = searchText,
                onValueChange = { text ->
                    searchText = text
                },
                placeholder = {
                    Text("Search...")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
            )
        }
    }
}
