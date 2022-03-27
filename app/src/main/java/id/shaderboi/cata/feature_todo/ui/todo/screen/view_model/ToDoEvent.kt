package id.shaderboi.cata.feature_todo.ui.todo.screen.view_model

import kotlinx.coroutines.CoroutineScope

sealed class ToDoEvent {
    class TitleTextInput(val text: String) : ToDoEvent()
    class DescriptionTextInput(val text: String) : ToDoEvent()
    class Load(val id: Int) : ToDoEvent()
    class Save(val coroutineScope: CoroutineScope) : ToDoEvent()
    object InvertCheck : ToDoEvent()
}