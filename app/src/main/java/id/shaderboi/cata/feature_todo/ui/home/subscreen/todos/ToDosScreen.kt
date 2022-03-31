package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import id.shaderboi.cata.R
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState
import id.shaderboi.cata.feature_todo.ui.home.common.HomeState
import id.shaderboi.cata.feature_todo.ui.home.common.rememberHomeState
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.FloatingActionButton
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.ToDoItem
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.TopBar
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.sort_todo.SortToDoModal
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosUIEvent
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ToDosScreen(
    appState: AppState,
    homeState: HomeState,
    toDosViewModel: ToDosViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(Unit) {
        toDosViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is ToDosUIEvent.Snackbar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        event.message,
                        event.actionLabel,
                        event.duration
                    )
                    when (result) {
                        SnackbarResult.Dismissed -> {
                            event.onDissmissed?.invoke()
                        }
                        SnackbarResult.ActionPerformed -> {
                            event.onActionPerformed?.invoke()
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(toDosViewModel)
        },
        floatingActionButton = {
            FloatingActionButton {
                homeState.homeViewModel.toggleToDoModal()
            }
        },
        bottomBar = {
            BottomAppBar {}
        }
    ) {
        if (toDosViewModel.toDosState.toDos.isEmpty() && toDosViewModel.toDosState.searchQuery.isBlank()) {
            val emptyComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {
                LottieAnimation(
                    emptyComposition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.height(300.dp),
                )
                Text(
                    "There's nothing here",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "You can add a To Do by pressing the + button on the corner",
                    textAlign = TextAlign.Center
                )
            }
        } else if (toDosViewModel.toDosState.toDos.isEmpty() && toDosViewModel.toDosState.searchQuery.isNotBlank()) {
            val notFoundComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.no_result_found))
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxHeight()
            ) {
                LottieAnimation(
                    notFoundComposition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.height(300.dp),
                )
                Text(
                    "Nothing can be found",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    "Maybe you have type the wrong query or... I don't know",
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(toDosViewModel.toDosState.toDos.size) { toDoIdx ->
                    ToDoItem(
                        appState = appState,
                        homeState = homeState,
                        toDo = toDosViewModel.toDosState.toDos[toDoIdx],
                        toDosViewModel = toDosViewModel
                    )
                }
            }
        }
    }

    SortToDoModal(toDosViewModel = toDosViewModel)
}


@Preview
@Composable
private fun ToDosScreenPreview() {
    ToDosScreen(
        appState = rememberAppState(),
        homeState = rememberHomeState(),
        toDosViewModel = hiltViewModel()
    )
}
