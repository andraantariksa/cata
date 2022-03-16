package id.shaderboi.cata.feature_todo.ui.home.todos

import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder

sealed class ToDosEvent {
    data class Order(val toDoOrder: ToDoOrder): ToDosEvent()
    data class Delete(val id: Int): ToDosEvent()
    object RestoreToDos: ToDosEvent()
}
