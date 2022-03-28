package id.shaderboi.cata.feature_todo.ui.home.todos

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import id.shaderboi.cata.R
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.home.todos.components.FloatingActionButton
import id.shaderboi.cata.feature_todo.ui.home.todos.components.ToDoItem
import id.shaderboi.cata.feature_todo.ui.home.todos.components.TopBar
import id.shaderboi.cata.feature_todo.ui.home.todos.components.sort_todo.SortToDoModal
import id.shaderboi.cata.feature_todo.ui.home.todos.view_model.ToDosUIEvent
import id.shaderboi.cata.feature_todo.ui.home.todos.view_model.ToDosViewModel
import id.shaderboi.cata.feature_todo.ui.home.view_model.HomeState
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
        if (toDosViewModel.toDosState.value.toDos.isEmpty() && toDosViewModel.toDosState.value.searchQuery.isBlank()) {
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
        } else if (toDosViewModel.toDosState.value.toDos.isEmpty() && toDosViewModel.toDosState.value.searchQuery.isNotBlank()) {
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
                items(toDosViewModel.toDosState.value.toDos.size) { toDoIdx ->
                    ToDoItem(
                        appState = appState,
                        homeState = homeState,
                        toDo = toDosViewModel.toDosState.value.toDos[toDoIdx],
                        toDosViewModel = toDosViewModel
                    )
                }
            }
        }
    }

    SortToDoModal(toDosViewModel = toDosViewModel)
}
