package id.shaderboi.cata.feature_todo.domain.repository

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.util.Order
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getToDo(
        order: ToDoOrder = ToDoOrder.Date(Order.Descending),
        searchQuery: String? = null
    ): Flow<List<ToDo>>

    suspend fun getToDo(id: Int): ToDo?
    suspend fun insertToDo(toDo: ToDo)
    suspend fun updateToDo(toDo: ToDo)
    suspend fun deleteToDo(id: Int)
}