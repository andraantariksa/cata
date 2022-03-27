package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.NoteRepository
import id.shaderboi.cata.feature_todo.domain.util.Order
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import kotlinx.coroutines.flow.Flow

class GetToDosUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        toDoOrder: ToDoOrder = ToDoOrder.Date(Order.Descending),
        searchQuery: String? = null
    ): Flow<List<ToDo>> {
        return repository.getToDo(toDoOrder, searchQuery)
    }
}