package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository

class DeleteToDoUseCase(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke(id: Int) = repository.deleteToDo(id)
}