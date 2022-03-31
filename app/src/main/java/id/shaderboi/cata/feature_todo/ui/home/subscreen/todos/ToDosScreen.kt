package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import id.shaderboi.cata.R
import id.shaderboi.cata.feature_todo.domain.util.Resource
import id.shaderboi.cata.feature_todo.ui.common.AppState
import id.shaderboi.cata.feature_todo.ui.common.components.AnimatedImageFull
import id.shaderboi.cata.feature_todo.ui.common.rememberAppState
import id.shaderboi.cata.feature_todo.ui.home.common.HomeState
import id.shaderboi.cata.feature_todo.ui.home.common.rememberHomeState
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.FloatingActionButton
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.ToDoItem
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.TopBar
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.sort_todo.SortToDoModal
import id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model.ToDosEvent
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
        toDosViewModel.onEvent(ToDosEvent.OnSearchTextChange(""))

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
        if (toDosViewModel.toDosState.isSearching) {
            ContentSearchedToDos(
                appState = appState,
                homeState = homeState,
                toDosViewModel = toDosViewModel
            )
        } else {
            ContentToDos(
                appState = appState,
                homeState = homeState,
                toDosViewModel = toDosViewModel
            )
        }
    }

    SortToDoModal(toDosViewModel = toDosViewModel)
}

@Composable
fun ContentSearchedToDos(appState: AppState, homeState: HomeState, toDosViewModel: ToDosViewModel) {
    when (val searchedToDos = toDosViewModel.toDosState.searchedToDos) {
        is Resource.Error -> {
            AnimatedImageFull(
                R.raw.error,
                "Uh oh",
                "Something bad happening in our apps. Please try again"
            )
        }
        is Resource.Loaded -> {
            if (searchedToDos.data.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(searchedToDos.data.size) { toDoIdx ->
                        ToDoItem(
                            appState = appState,
                            homeState = homeState,
                            toDo = searchedToDos.data[toDoIdx],
                            toDosViewModel = toDosViewModel
                        )
                    }
                }
            } else {
                AnimatedImageFull(
                    R.raw.no_result_found,
                    "Nothing can be found",
                    "Maybe you have type the wrong query or... I don't know"
                )
            }
        }
        is Resource.Loading -> {
            AnimatedImageFull(
                R.raw.loading_box
            )
        }
    }
}

@Composable
fun ContentToDos(appState: AppState, homeState: HomeState, toDosViewModel: ToDosViewModel) {
    if (toDosViewModel.toDosState.toDos.isNotEmpty()) {
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
    } else {
        AnimatedImageFull(
            R.raw.empty,
            "There's nothing here",
            "You can add a To Do by pressing the + button on the corner"
        )
    }
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
