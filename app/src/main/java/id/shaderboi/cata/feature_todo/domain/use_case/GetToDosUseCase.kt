package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.NoteRepository
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.util.Order
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetToDosUseCase(
    private val repository: NoteRepository
) {
    operator fun invoke(
        toDoOrder: ToDoOrder = ToDoOrder.Date(Order.Descending)
    ): Flow<List<ToDo>> {
        return repository.getToDo().map { notes ->
            when (toDoOrder.order) {
                Order.Ascending -> when (toDoOrder) {
                    is ToDoOrder.Date -> notes.sortedBy { it.timestamp }
                    is ToDoOrder.Title -> notes.sortedBy { it.title }
                }
                Order.Descending -> when (toDoOrder) {
                    is ToDoOrder.Date -> notes.sortedByDescending { it.timestamp }
                    is ToDoOrder.Title -> notes.sortedByDescending { it.title }
                }
            }
        }
    }
}