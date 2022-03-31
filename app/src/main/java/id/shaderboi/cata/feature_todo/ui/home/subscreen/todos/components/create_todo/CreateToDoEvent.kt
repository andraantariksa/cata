package id.shaderboi.cata.feature_todo.ui.home.subscreen.todos.components.create_todo

sealed class CreateToDoEvent {
    class ChangedTitle(val text: String) : CreateToDoEvent()
    class ChangedDescription(val text: String) : CreateToDoEvent()
    class CreateToDo(val onCompletion: () -> Unit) : CreateToDoEvent()
}