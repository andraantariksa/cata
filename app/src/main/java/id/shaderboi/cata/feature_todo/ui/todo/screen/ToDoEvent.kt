package id.shaderboi.cata.feature_todo.ui.todo.screen

sealed class ToDoEvent {
    class TitleTextInput(val text: String) : ToDoEvent()
    class DescriptionTextInput(val text: String) : ToDoEvent()
    class Load(val id: Int) : ToDoEvent()
    class Save(val id: Int): ToDoEvent()
}