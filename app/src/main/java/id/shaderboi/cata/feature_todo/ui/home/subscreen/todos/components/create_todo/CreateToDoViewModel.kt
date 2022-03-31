package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.create_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateToDoViewModel @Inject constructor(
    private val toDoUseCase: ToDoUseCases
) : ViewModel() {
    private var _toDo by mutableStateOf(ToDo())
    val toDo get() = _toDo

    private var _isLoading by mutableStateOf(false)
    val isLoading get() = _isLoading

    private var _isCanAdd by mutableStateOf(false)
    val isCanAdd get() = _isCanAdd

    fun onEvent(event: CreateToDoEvent) {
        when (event) {
            is CreateToDoEvent.ChangedDescription -> {
                _toDo = toDo.copy(
                    description = event.text,
                )

                checkAddAvailability()
            }
            is CreateToDoEvent.ChangedTitle -> {
                _toDo = toDo.copy(
                    title = event.text,
                )

                checkAddAvailability()
            }
            is CreateToDoEvent.CreateToDo -> createToDo(event.onCompletion)
        }
    }

    private fun createToDo(
        onCompletion: () -> Unit = {},
    ) {
        _isLoading = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                toDoUseCase.insertToDo(toDo)
                // Reset create to do fields
                _toDo = ToDo()
            } catch (ex: Exception) {
                throw ex
            } finally {
                _isLoading = false
            }
        }.invokeOnCompletion {
            onCompletion()
        }
    }

    private fun checkAddAvailability() {
        val availability = toDo.title.isNotBlank()
        if (availability != isCanAdd) {
            _isCanAdd = availability
        }
    }
}