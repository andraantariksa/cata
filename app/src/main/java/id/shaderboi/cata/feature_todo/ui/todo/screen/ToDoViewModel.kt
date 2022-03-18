package id.shaderboi.cata.feature_todo.ui.todo.screen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import id.shaderboi.cata.feature_todo.domain.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteTextState(
    val title: String = "",
    val description: String = "",
    val priority: ToDoPriority = ToDoPriority.None
)

@HiltViewModel
class ToDoViewModel @Inject constructor(
    private val toDoUseCase: ToDoUseCases
) : ViewModel() {
    private val _noteState = mutableStateOf<Resource<NoteTextState, Unit>>(Resource.Loading())
    val noteState get() = _noteState

    private val _uiEvent = MutableSharedFlow<ToDoUIEvent>()
    val uiEvent get() = _uiEvent

    private fun fetchToDo(id: Int) {
        viewModelScope.launch {
            try {
                val toDo = toDoUseCase.getToDo(id)
                if (toDo != null) {
                    _noteState.value = Resource.Loaded(
                        NoteTextState(
                            title = toDo.title,
                            description = toDo.content
                        )
                    )
                } else {
                    _noteState.value = Resource.Error(Unit)
                }
            } catch (ex: Exception) {
                _noteState.value = Resource.Error(Unit)
                _uiEvent.emit(ToDoUIEvent.ShowSnackBar(ex.message ?: "Unknown error"))
            }
        }
    }

    private fun saveToDo(id: Int) {
        viewModelScope.launch {
            _uiEvent.emit(ToDoUIEvent.LockInput)
            val state = _noteState.value
            if (state is Resource.Loaded<NoteTextState, Unit>) {
                try {
                    toDoUseCase.updateToDo(
                        ToDo(
                            id,
                            state.data.title,
                            state.data.description,
                            state.data.priority,
                            System.currentTimeMillis()
                        )
                    )
                } catch (ex: Exception) {
                    _uiEvent.emit(ToDoUIEvent.ShowSnackBar(ex.message ?: "Unknown error"))
                } finally {
                    _uiEvent.emit(ToDoUIEvent.UnlockInput)
                }
                _uiEvent.emit(ToDoUIEvent.CloseToDoScreen)
            } else {
                throw IllegalAccessError()
            }
        }
    }

    fun onEvent(event: ToDoEvent) {
        when (event) {
            is ToDoEvent.DescriptionTextInput -> {
                val state = _noteState.value
                if (state is Resource.Loaded<NoteTextState, Unit>) {
                    state.data = state.data.copy(
                        title = state.data.title,
                        description = event.text
                    )
                    _noteState.value = state
                } else {
                    throw IllegalAccessError()
                }
            }
            is ToDoEvent.TitleTextInput -> {
                val state = _noteState.value
                if (state is Resource.Loaded<NoteTextState, Unit>) {
                    state.data = state.data.copy(
                        title = event.text,
                        description = state.data.description
                    )
                    _noteState.value = state
                } else {
                    throw IllegalAccessError()
                }
            }
            is ToDoEvent.Load -> fetchToDo(event.id)
            is ToDoEvent.Save -> saveToDo(event.id)
        }
    }
}
