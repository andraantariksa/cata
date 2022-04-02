package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.view_model

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.Order
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrderField
import id.shaderboi.cata.feature_todo.domain.util.Resource

data class ToDosState(
    val toDos: List<ToDo> = emptyList(),
    val toDoOrder: ToDoOrder = ToDoOrder(ToDoOrderField.Date, Order.Descending),
    val isSearching: Boolean = false,
    val searchedToDos: Resource<List<ToDo>, Unit> = Resource.Loading(),
    var searchQuery: String = ""
)