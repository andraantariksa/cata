package id.shaderboi.cata.feature_todo.ui.home.todos.components.create_todo

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import id.shaderboi.cata.feature_todo.ui.home.HomeViewModel

@Composable
fun CreateToDoOverlay(
    homeViewModel: HomeViewModel,
    toDosViewModel: CreateToDoViewModel = hiltViewModel()
) {
    var textTitle by remember { mutableStateOf("") }
    var textDescription by remember { mutableStateOf("") }
    val titleFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    val fontSize = 25.sp
    val fontWeight = FontWeight.Bold

    val density = LocalDensity.current

    Box {
        AnimatedVisibility(
            visible = homeViewModel.isCreateToDoModalOpened.value,
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
                visible = homeViewModel.isCreateToDoModalOpened.value,
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
                            onValueChange = {
                                if (it.lastOrNull() == '\n') {
                                    descriptionFocusRequester.requestFocus()
                                    return@TextField
                                }
                                textTitle = it
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
                            onValueChange = {
                                textDescription = it
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
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 5.dp,
                                    vertical = 8.dp
                                ),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = {
                                    toDosViewModel.createToDo(textTitle, textDescription)
                                        .invokeOnCompletion {
                                            homeViewModel.toggleToDoModal()
                                        }
                                }
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }
    }
}