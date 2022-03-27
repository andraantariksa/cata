package id.shaderboi.cata.feature_todo.ui.home.todos.view_model

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.util.Order
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder

data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val toDoOrder: ToDoOrder = ToDoOrder.Date(Order.Descending),
    var searchQuery: String = ""
)