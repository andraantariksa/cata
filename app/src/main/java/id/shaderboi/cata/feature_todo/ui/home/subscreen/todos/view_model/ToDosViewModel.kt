package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import id.shaderboi.cata.feature_todo.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDosViewModel @Inject constructor(
    private val toDoUseCases: ToDoUseCases
) : ViewModel() {
    private var _toDosState by mutableStateOf(ToDosState())
    val toDosState get() = _toDosState

    private var _isSortToDoModalOpened by mutableStateOf(false)
    val isSortToDoModalOpened get() = _isSortToDoModalOpened

    private var lastDeletedToDo: ToDo? = null

    private val _uiEvent = MutableSharedFlow<ToDosUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var searchToDoJob: Job? = null
    private var searchInputNotesJob: Job? = null

    init {
        getToDo(toDosState.toDoOrder)
    }

    fun onEvent(event: ToDosEvent) {
        when (event) {
            is ToDosEvent.Delete -> deleteToDo(event.toDo)
            is ToDosEvent.Order -> {
                if (toDosState.toDoOrder == event.toDoOrder) {
                    return
                }

                _toDosState = toDosState.copy(toDoOrder = event.toDoOrder)

                getToDo(event.toDoOrder)
            }
            ToDosEvent.RestoreToDos -> {
                viewModelScope.launch {
                    toDoUseCases.insertToDo(lastDeletedToDo ?: return@launch)
                    lastDeletedToDo = null
                }
            }
            is ToDosEvent.OnSearchTextChange -> {
                val isSearching = event.string.isNotBlank()
                _toDosState = toDosState.copy(
                    searchQuery = event.string,
                    isSearching = isSearching
                )

                searchInputNotesJob?.cancel()

                if (isSearching) {
                    searchInputNotesJob = viewModelScope.launch {
                        delay(1000)
                        searchToDo(toDosState.toDoOrder, toDosState.searchQuery)
                    }
                }
            }
            is ToDosEvent.ToggleToDoCheck -> toggleToDoCheck(event.toDo)
            ToDosEvent.ToggleSortToDoModal -> {
                _isSortToDoModalOpened = !isSortToDoModalOpened
            }
        }
    }

    private fun toggleToDoCheck(toDo: ToDo) = viewModelScope.launch(Dispatchers.IO) {
        toDoUseCases.updateToDo(toDo.copy(checked = !toDo.checked))
    }

    private fun deleteToDo(toDo: ToDo) = viewModelScope.launch(Dispatchers.IO) {
        lastDeletedToDo = toDo
        toDoUseCases.deleteNotes(toDo.id)

        _uiEvent.emit(
            ToDosUIEvent.Snackbar(
                "1 deleted",
                "Undo",
                SnackbarDuration.Long,
                onActionPerformed = {
                    viewModelScope.launch(Dispatchers.IO) {
                        lastDeletedToDo?.let { toDo ->
                            toDoUseCases.insertToDo(toDo)
                        }
                    }
                }
            )
        )
    }

    private fun searchToDo(toDoOrder: ToDoOrder, searchQuery: String) {
        searchToDoJob?.cancel()
        _toDosState = toDosState.copy(
            searchedToDos = Resource.Loading(),
        )

        searchToDoJob = viewModelScope.launch(Dispatchers.IO) {
            toDoUseCases
                .getToDos(toDoOrder, searchQuery)
                .collectLatest { searchedToDos ->
                    _toDosState = toDosState.copy(
                        searchedToDos = Resource.Loaded(searchedToDos),
                    )
                }
        }
    }

    private fun getToDo(toDoOrder: ToDoOrder) = viewModelScope.launch(Dispatchers.IO) {
        toDoUseCases
            .getToDos(toDoOrder, null)
            .collectLatest { toDos ->
                _toDosState = toDosState.copy(
                    toDos = toDos,
                )
            }
    }
}