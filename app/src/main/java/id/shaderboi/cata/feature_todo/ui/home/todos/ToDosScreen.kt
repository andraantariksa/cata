package id.shaderboi.cata.feature_todo.ui.home.todos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import id.shaderboi.cata.feature_todo.ui.AppState
import id.shaderboi.cata.feature_todo.ui.home.HomeState
import id.shaderboi.cata.feature_todo.ui.home.todos.components.FloatingActionButton
import id.shaderboi.cata.feature_todo.ui.home.todos.components.ToDo
import id.shaderboi.cata.feature_todo.ui.home.todos.components.TopBar

@Composable
fun ToDosScreen(
    appState: AppState,
    homeState: HomeState,
    toDosViewModel: ToDosViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar()
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .background(Color.Red)
        ) {
            items(toDosViewModel.toDos.value.toDos.size) { toDoIdx ->
                ToDo(
                    appState = appState,
                    homeState = homeState,
                    toDo = toDosViewModel.toDos.value.toDos[toDoIdx],
                    toDosViewModel = toDosViewModel
                )
            }
        }
    }
}
