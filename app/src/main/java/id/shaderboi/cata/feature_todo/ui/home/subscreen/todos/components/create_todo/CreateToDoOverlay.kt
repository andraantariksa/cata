package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.create_todo

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsWithImePadding
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority
import id.shaderboi.cata.feature_todo.ui.common.components.SelectDialog
import id.shaderboi.cata.feature_todo.ui.home.view_model.HomeViewModel

@Composable
fun CreateToDoOverlay(
    homeViewModel: HomeViewModel,
    createToDoViewModel: CreateToDoViewModel = hiltViewModel()
) {
    val createToDoState = createToDoViewModel.toDo
    val textTitle = createToDoState.title
    val textDescription = createToDoState.description

    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    var showSelectPriorityModal by remember { mutableStateOf(false) }

    val density = LocalDensity.current

    Box {
        AnimatedVisibility(
            visible = homeViewModel.isCreateToDoModalOpened,
            enter = fadeIn(animationSpec = tween()),
            exit = fadeOut(animationSpec = tween()),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color(0.0f, 0.0f, 0.0f, 0.7f))
                    .clickable {
                        homeViewModel.toggleToDoModal()
                    }
            )
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            AnimatedVisibility(
                visible = homeViewModel.isCreateToDoModalOpened,
                enter = slideInVertically(animationSpec = tween()) {
                    with(density) { 500.dp.roundToPx() }
                },
                exit = slideOutVertically(animationSpec = tween()) {
                    with(density) { 500.dp.roundToPx() }
                },
            ) {
                Surface(
                    modifier = Modifier.navigationBarsWithImePadding()
                ) {
                    Column {
                        TextField(
                            value = textTitle,
                            onValueChange = { text ->
                                if (text.lastOrNull() == '\n') {
                                    descriptionFocusRequester.requestFocus()
                                    return@TextField
                                }
                                createToDoViewModel.onEvent(CreateToDoEvent.ChangedTitle(text))
                            },
                            modifier = Modifier
                                .defaultMinSize(
                                    minWidth = TextFieldDefaults.MinWidth,
                                    minHeight = TextFieldDefaults.MinHeight
                                )
                                .fillMaxWidth()
                                .onKeyEvent { keyEvent ->
                                    when (keyEvent.nativeKeyEvent.keyCode) {
                                        NativeKeyEvent.KEYCODE_ENTER, NativeKeyEvent.KEYCODE_NUMPAD_ENTER -> {
                                            descriptionFocusRequester.requestFocus()
                                            true
                                        }
                                        else -> false
                                    }
                                }
                                .focusOrder(titleFocusRequester)
                                .focusRequester(titleFocusRequester),
                            placeholder = {
                                Text("Title", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                            },
                            textStyle = LocalTextStyle.current.copy(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent
                            ),
                            readOnly = createToDoViewModel.isLoading
                        )
                        TextField(
                            value = textDescription,
                            onValueChange = { text ->
                                createToDoViewModel.onEvent(CreateToDoEvent.ChangedDescription(text))
                            },
                            modifier = Modifier
                                .defaultMinSize(
                                    minWidth = TextFieldDefaults.MinWidth,
                                )
                                .heightIn(100.dp, 300.dp)
                                .fillMaxWidth()
                                .focusOrder(descriptionFocusRequester)
                                .focusRequester(descriptionFocusRequester),
                            placeholder = {
                                Text("Description")
                            },
                            colors = TextFieldDefaults.textFieldColors(
                                backgroundColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent
                            ),
                            readOnly = createToDoViewModel.isLoading
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 15.dp,
                                    vertical = 8.dp
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = {
                                    if (!createToDoViewModel.isLoading) {
                                        showSelectPriorityModal = true
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Flag,
                                    contentDescription = "Select priority",
                                    tint = createToDoViewModel.toDo.priority.color,
                                )
                            }

                            Button(
                                onClick = {
                                    createToDoViewModel.onEvent(
                                        CreateToDoEvent.CreateToDo {
                                            homeViewModel.toggleToDoModal()
                                        }
                                    )
                                },
                                modifier = Modifier
                                    .alpha(
                                        if (
                                            !createToDoViewModel.isLoading &&
                                            createToDoViewModel.isCanAdd
                                        )
                                            1.0F
                                        else
                                            0.5F
                                    ),
                                enabled = !createToDoViewModel.isLoading &&
                                        createToDoViewModel.isCanAdd
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSelectPriorityModal) {
        SelectDialog(
            selectedIndex = createToDoViewModel.toDo.priority.ordinal,
            options = ToDoPriority.values(),
            title = {
                Text("Select priority", fontWeight = FontWeight.Medium, fontSize = 20.sp)
            },
            onDismissRequest = {
                showSelectPriorityModal = false
            },
            onClick = { idx, option ->
                createToDoViewModel.onEvent(CreateToDoEvent.ChangedPriority(option))
                showSelectPriorityModal = false
            },
            itemComponent = { idx, option, onItemClick ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick() }
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option == createToDoViewModel.toDo.priority,
                        onClick = onItemClick
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = option.toString()
                        )
                        Icon(
                            imageVector = Icons.Default.Flag,
                            contentDescription = "Priority $option",
                            tint = option.color
                        )
                    }
                }
            }
        )
    }

}