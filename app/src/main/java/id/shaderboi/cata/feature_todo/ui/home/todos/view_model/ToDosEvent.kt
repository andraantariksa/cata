package id.shaderboi.cata.feature_todo.ui.home.todos.view_model

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder

sealed class ToDosEvent {
    class Order(val toDoOrder: ToDoOrder) : ToDosEvent()
    class Delete(val toDo: ToDo) : ToDosEvent()
    object RestoreToDos : ToDosEvent()
    class OnSearchTextChange(val string: String) : ToDosEvent()
    class ToggleToDoCheck(val toDo: ToDo) : ToDosEvent()
    object ToggleSortToDoModal : ToDosEvent()
}
