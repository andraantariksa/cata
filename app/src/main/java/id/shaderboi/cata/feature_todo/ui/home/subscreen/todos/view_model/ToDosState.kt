package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.Order
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrderField

data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val toDoOrder: ToDoOrder = ToDoOrder(ToDoOrderField.Date, Order.Descending),
    var searchQuery: String = ""
)