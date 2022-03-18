package id.shaderboi.cata.feature_todo.ui.home.todos.components.create_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority
import id.shaderboi.cata.feature_todo.domain.use_case.ToDoUseCases
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateToDoViewModel @Inject constructor(
    private val toDoUseCase: ToDoUseCases
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading get() = _isLoading

    fun createToDo(title: String, content: String, priority: ToDoPriority): Job {
        _isLoading.value = true
        return viewModelScope.launch {
            try {
                toDoUseCase.insertToDo(
                    ToDo(
                        title,
                        content,
                        priority,
                        System.currentTimeMillis()
                    )
                )
            } catch (ex: Exception) {
                throw ex;
            } finally {
                _isLoading.value = false
            }
        }
    }
}