package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model

import androidx.compose.material.SnackbarDuration
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
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

    private var lastDeletedToDo: ToDo? = null

    private val _uiEvent = MutableSharedFlow<ToDosUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private var getNotesJob: Job? = null
    private var searchInputNotesJob: Job? = null

    private var deleteJob: Job? = null

    private val _isSortToDoModalOpened = mutableStateOf(false)
    val isSortToDoModalOpened: State<Boolean> = _isSortToDoModalOpened

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
                _toDosState = toDosState.copy(searchQuery = event.string)

                searchInputNotesJob?.cancel()
                searchInputNotesJob = viewModelScope.launch {
                    delay(1000)
                    getToDo(toDosState.toDoOrder, toDosState.searchQuery)
                }
            }
            is ToDosEvent.ToggleToDoCheck -> toggleToDoCheck(event.toDo)
            ToDosEvent.ToggleSortToDoModal -> toggleSortModal()
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

    private fun getToDo(toDoOrder: ToDoOrder, _searchQuery: String? = null) {
        getNotesJob?.cancel()

        val searchQuery = _searchQuery?.ifBlank { null }

        getNotesJob = viewModelScope.launch(Dispatchers.IO) {
            toDoUseCases
                .getToDos(toDoOrder, searchQuery)
                .collectLatest { toDos ->
                    _toDosState = toDosState.copy(
                        toDos = toDos,
                    )
                }
        }
    }

    private fun toggleSortModal() {
        _isSortToDoModalOpened.value = !_isSortToDoModalOpened.value
    }
}