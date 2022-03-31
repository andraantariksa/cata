package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository

class GetToDoUseCase(
    private val repository: ToDoRepository
) {
    suspend operator fun invoke(
        id: Int
    ): ToDo? = repository.getToDo(id)
}