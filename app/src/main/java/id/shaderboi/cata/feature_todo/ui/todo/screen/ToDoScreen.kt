package id.shaderboi.cata.feature_todo.ui.todo.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import id.shaderboi.cata.feature_todo.domain.util.Resource
import id.shaderboi.cata.feature_todo.ui.AppState
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
    val lockInput = remember { mutableStateOf(false) }

    val fontSize = 25.sp
    val fontWeight = FontWeight.Bold

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
                        lockInput.value = true
                    }
                    ToDoUIEvent.UnlockInput -> {
                        lockInput.value = false
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
                    BottomAppBar(appState = appState) {
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

                        TextButton(
                            onClick = {
                                toDoViewModel.onEvent(ToDoEvent.InvertCheck)
                                toDoViewModel.onEvent(ToDoEvent.Save(appState.appScope))
                            }
                        ) {
                            Text(
                                if (state.data.checked)
                                    "Mark as incomplete"
                                else
                                    "Mark as complete"
                            )
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
                            Text("Title", fontSize = fontSize, fontWeight = fontWeight)
                        },
                        textStyle = LocalTextStyle.current.copy(
                            fontSize = fontSize,
                            fontWeight = fontWeight
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
            }
            is Resource.Loading -> {
                Column {
                    Text("Loading")
                }
            }
        }
    }
}
