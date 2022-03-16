package id.shaderboi.cata.feature_todo.ui.home.todos

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.util.Order

data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val toDoOrder: ToDoOrder = ToDoOrder.Date(Order.Descending),
    var lastDeletedToDo: ToDo? = null
)