package id.shaderboi.cata.feature_todo.ui.todo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Flag
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.NativeKeyEvent
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority
import id.shaderboi.cata.feature_todo.domain.util.Resource
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.common.components.SelectDialog
import id.shaderboi.cata.feature_todo.ui.todo.components.BottomAppBar
import id.shaderboi.cata.feature_todo.ui.todo.screen.view_model.ToDoEvent
import id.shaderboi.cata.feature_todo.ui.todo.screen.view_model.ToDoUIEvent
import id.shaderboi.cata.feature_todo.ui.todo.screen.view_model.ToDoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ToDoScreen(
    appState: AppState,
    toDoId: Int,
    toDoViewModel: ToDoViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    var lockInput by remember { mutableStateOf(false) }
    var selectingPriority by remember { mutableStateOf(false) }

    BackHandler {
        toDoViewModel.onEvent(ToDoEvent.Save(appState.appScope))
        appState.navHostController.popBackStack()
    }

    LaunchedEffect(Unit) {
        launch(Dispatchers.IO) {
            toDoViewModel.onEvent(ToDoEvent.Load(toDoId))
        }

        launch(Dispatchers.Main) {
            toDoViewModel.uiEvent.collectLatest { event ->
                when (event) {
                    ToDoUIEvent.CloseToDoScreen -> {
                        appState.navHostController.popBackStack()
                    }
                    ToDoUIEvent.LockInput -> {
                        lockInput = true
                    }
                    ToDoUIEvent.UnlockInput -> {
                        lockInput = false
                    }
                    is ToDoUIEvent.ShowSnackBar -> {
                        scaffoldState.snackbarHostState.showSnackbar(
                            event.title,
                            event.actionLabel,
                            event.duration
                        )
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            when (val state = toDoViewModel.toDoState.value) {
                is Resource.Loaded -> {
                    BottomAppBar(
                        navigationIcon = {
                            if (appState.navHostController.previousBackStackEntry != null) {
                                IconButton(
                                    onClick = {
                                        toDoViewModel.onEvent(ToDoEvent.Save(appState.appScope))
                                        appState.navHostController.popBackStack()
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(
                                onClick = {
                                    selectingPriority = true
                                }
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
                }
                else -> {}
            }
        },
//        modifier = Modifier.padding(5.dp)
    ) {
        val state = toDoViewModel.toDoState.value
        when (state) {
            is Resource.Error -> {
                Column {
                    Text("Error")
                }
            }
            is Resource.Loaded -> {
                val textTitle = state.data.title
                val textDescription = state.data.description

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = textTitle,
                        onValueChange = { text ->
                            if (text.lastOrNull() == '\n') {
                                descriptionFocusRequester.requestFocus()
                                return@TextField
                            }
                            toDoViewModel.onEvent(ToDoEvent.TitleTextInput(text))
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
                    )
                    TextField(
                        value = textDescription,
                        onValueChange = { text ->
                            toDoViewModel.onEvent(ToDoEvent.DescriptionTextInput(text))
                        },
                        modifier = Modifier
                            .defaultMinSize(
                                minWidth = TextFieldDefaults.MinWidth,
                                minHeight = TextFieldDefaults.MinHeight
                            )
                            .fillMaxSize()
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
                    )
                }

                if (selectingPriority) {
                    SelectDialog(
                        selectedIndex = state.data.priority.ordinal,
                        options = ToDoPriority.values(),
                        title = {
                            Text(
                                "Select priority",
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp
                            )
                        },
                        onDismissRequest = {
                            selectingPriority = false
                        },
                        onClick = { idx, selectedIdx, option ->
                            toDoViewModel.onEvent(ToDoEvent.ChangePriority(option))
                            selectingPriority = false
                        },
                        itemComponent = { idx, selectedIdx, option, onItemClick ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onItemClick() }
                                    .padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = idx == selectedIdx,
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
            is Resource.Loading -> {
                Column {
                    Text("Loading")
                }
            }
        }
    }
}
