package id.shaderboi.cata.feature_todo.ui.todo.screen.view_model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import id.shaderboi.cata.feature_todo.domain.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoUseCase: ToDoUseCases
) : ViewModel() {
    private val _toDoState = mutableStateOf<Resource<ToDo, Unit>>(Resource.Loading())
    val toDoState: State<Resource<ToDo, Unit>> = _toDoState

    private val _uiEvent = MutableSharedFlow<ToDoUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun fetchToDo(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val toDo = toDoUseCase.getToDo(id)
            if (toDo != null) {
                _toDoState.value = Resource.Loaded(toDo)
            } else {
                _toDoState.value = Resource.Error(Unit)
            }
        } catch (ex: Exception) {
            _toDoState.value = Resource.Error(Unit)
            _uiEvent.emit(ToDoUIEvent.ShowSnackBar(ex.message ?: "Unknown error"))
        }
    }

    private fun saveToDo(coroutineScope: CoroutineScope) = coroutineScope.launch(Dispatchers.IO) {
        _uiEvent.emit(ToDoUIEvent.LockInput)
        val state = _toDoState.value
        if (state is Resource.Loaded<ToDo, Unit>) {
            try {
                toDoUseCase.updateToDo(state.data.copy(updatedAt = System.currentTimeMillis()))
            } catch (ex: Exception) {
                _uiEvent.emit(ToDoUIEvent.ShowSnackBar(ex.message ?: "Unknown error"))
            } finally {
                _uiEvent.emit(ToDoUIEvent.UnlockInput)
            }
//            _uiEvent.emit(ToDoUIEvent.CloseToDoScreen)
        } else {
            throw IllegalAccessError()
        }
    }

    fun onEvent(event: ToDoEvent) {
        when (event) {
            is ToDoEvent.DescriptionTextInput -> {
                when (val state = toDoState.value) {
                    is Resource.Loaded<ToDo, Unit> -> {
                        _toDoState.value = Resource.Loaded(
                            state.data.copy(
                                description = event.text
                            )
                        )
                    }
                    else -> {
                        throw IllegalAccessError()
                    }
                }
            }
            is ToDoEvent.TitleTextInput -> {
                when (val state = toDoState.value) {
                    is Resource.Loaded<ToDo, Unit> -> {
                        _toDoState.value = Resource.Loaded(
                            state.data.copy(
                                title = event.text
                            )
                        )
                    }
                    else -> {
                        throw IllegalAccessError()
                    }
                }
            }
            ToDoEvent.InvertCheck -> {
                when (val state = toDoState.value) {
                    is Resource.Loaded<ToDo, Unit> -> {
                        _toDoState.value = Resource.Loaded(
                            state.data.copy(
                                checked = !state.data.checked
                            )
                        )
                    }
                    else -> {
                        throw IllegalAccessError()
                    }
                }
            }
            is ToDoEvent.Load -> fetchToDo(event.id)
            is ToDoEvent.Save -> saveToDo(event.coroutineScope)
        }
    }
}
