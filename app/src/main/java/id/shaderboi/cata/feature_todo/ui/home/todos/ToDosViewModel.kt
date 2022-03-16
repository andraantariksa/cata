package id.shaderboi.cata.feature_todo.ui.home.todos

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.util.Order
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDosViewModel @Inject constructor(
    private val toDoUseCases: ToDoUseCases
): ViewModel() {
    private val _toDos = mutableStateOf(ToDosState())
    val toDos: State<ToDosState> get() = _toDos

    private var getNotesJob: Job? = null

    init {
        getToDo(ToDoOrder.Date(Order.Descending))
    }

    fun onEvent(event: ToDosEvent) {
        when(event) {
            is ToDosEvent.Delete -> {
                viewModelScope.launch {
                    toDoUseCases.deleteNotes(event.id)
                }
            }
            is ToDosEvent.Order -> {
                if (toDos.value.toDoOrder == event.toDoOrder) {
                    return
                }
                getToDo(event.toDoOrder)
            }
            ToDosEvent.RestoreToDos -> {
                viewModelScope.launch {
                    toDoUseCases.insertToDo(_toDos.value.lastDeletedToDo ?: return@launch)
                    _toDos.value.lastDeletedToDo = null
                }
            }
        }
    }

    fun getToDo(toDoOrder: ToDoOrder) {
        getNotesJob?.cancel()
        getNotesJob = toDoUseCases.getToDos(toDoOrder).onEach { todo ->
            _toDos.value = _toDos.value.copy(
                toDos = todo,
                toDoOrder = toDoOrder
            )
        }.launchIn(viewModelScope)
    }
}