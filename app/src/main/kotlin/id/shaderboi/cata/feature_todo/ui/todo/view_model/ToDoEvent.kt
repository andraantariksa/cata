package id.shaderboi.cata.feature_todo.ui.todo.view_model

import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority
import kotlinx.coroutines.CoroutineScope

sealed class ToDoEvent {
    class TitleTextInput(val text: String) : ToDoEvent()
    class DescriptionTextInput(val text: String) : ToDoEvent()
    class Load(val id: Int) : ToDoEvent()
    class Save(val coroutineScope: CoroutineScope) : ToDoEvent()
    class ChangePriority(val priority: ToDoPriority) : ToDoEvent()
    object InvertCheck : ToDoEvent()
}