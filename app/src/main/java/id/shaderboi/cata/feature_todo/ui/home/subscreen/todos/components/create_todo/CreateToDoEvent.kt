package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.create_todo

import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority

sealed class CreateToDoEvent {
    class ChangedTitle(val text: String) : CreateToDoEvent()
    class ChangedDescription(val text: String) : CreateToDoEvent()
    class CreateToDo(val onCompletion: () -> Unit) : CreateToDoEvent()
    class ChangedPriority(val priority: ToDoPriority) : CreateToDoEvent()
}