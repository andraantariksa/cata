package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow

class GetToDosUseCase(
    private val repository: ToDoRepository
) {
    operator fun invoke(
        toDoOrder: ToDoOrder,
        searchQuery: String? = null
    ): Flow<List<ToDo>> = repository.getToDo(toDoOrder, searchQuery)
}