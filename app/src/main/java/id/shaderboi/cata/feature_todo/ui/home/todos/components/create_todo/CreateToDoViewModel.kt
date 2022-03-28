package id.shaderboi.cata.feature_todo.ui.home.todos.components.create_todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateToDoViewModel @Inject constructor(
    private val toDoUseCase: ToDoUseCases
) : ViewModel() {
    private val _toDo = mutableStateOf(ToDo())
    val toDo: State<ToDo> = _toDo

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _isCanAdd = mutableStateOf(false)
    val isCanAdd: State<Boolean> = _isCanAdd

    fun onEvent(event: CreateToDoEvent) {
        when (event) {
            is CreateToDoEvent.ChangedDescription -> {
                _toDo.value = _toDo.value.copy(
                    description = event.text,
                )

                checkAddAvailability()
            }
            is CreateToDoEvent.ChangedTitle -> {
                _toDo.value = _toDo.value.copy(
                    title = event.text,
                )

                checkAddAvailability()
            }
            is CreateToDoEvent.CreateToDo -> createToDo(event.onCompletion)
        }
    }

    private fun createToDo(
        onCompletion: () -> Unit,
    ) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                toDoUseCase.insertToDo(toDo.value)
                _toDo.value = ToDo()
            } catch (ex: Exception) {
                throw ex
            } finally {
                _isLoading.value = false
            }
        }.invokeOnCompletion {
            onCompletion()
        }
    }

    private fun checkAddAvailability() {
        val availability = toDo.value.title.isNotBlank()
        if (availability != isCanAdd.value) {
            _isCanAdd.value = availability
        }
    }
}