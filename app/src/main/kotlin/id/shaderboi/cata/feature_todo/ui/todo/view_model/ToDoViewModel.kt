package id.shaderboi.cata.feature_todo.ui.todo.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    private var _toDoState by mutableStateOf<Resource<ToDo, Unit>>(Resource.Loading())
    val toDoState get() = _toDoState

    private val _uiEvent = MutableSharedFlow<ToDoUIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private fun fetchToDo(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val toDo = toDoUseCase.getToDo(id)
            if (toDo != null) {
                _toDoState = Resource.Loaded(toDo)
            } else {
                _toDoState = Resource.Error(Unit)
            }
        } catch (ex: Exception) {
            _toDoState = Resource.Error(Unit)
            _uiEvent.emit(ToDoUIEvent.ShowSnackBar(ex.message ?: "Unknown error"))
        }
    }

    private fun saveToDo(coroutineScope: CoroutineScope = viewModelScope) =
        coroutineScope.launch(Dispatchers.IO) {
            _uiEvent.emit(ToDoUIEvent.LockInput)
            val state = _toDoState
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
        when (val state = toDoState) {
            is Resource.Loaded<ToDo, Unit> -> when (event) {
                is ToDoEvent.DescriptionTextInput -> {
                    _toDoState = Resource.Loaded(
                        state.data.copy(
                            description = event.text
                        )
                    )
                }
                is ToDoEvent.TitleTextInput -> {
                    _toDoState = Resource.Loaded(
                        state.data.copy(
                            title = event.text
                        )
                    )
                }
                is ToDoEvent.ChangePriority -> {
                    _toDoState = Resource.Loaded(
                        state.data.copy(
                            priority = event.priority
                        )
                    )
                }
                ToDoEvent.InvertCheck -> {
                    _toDoState = Resource.Loaded(
                        state.data.copy(
                            checked = !state.data.checked
                        )
                    )
                }
                is ToDoEvent.Save -> saveToDo(event.coroutineScope)
                else -> throw IllegalArgumentException()
            }
            else -> when (event) {
                is ToDoEvent.Load -> fetchToDo(event.id)
                else -> throw IllegalArgumentException()
            }
        }
    }
}
