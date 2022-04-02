package id.shaderboi.cata.feature_todo.domain.repository

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    fun getToDo(
        orderField: ToDoOrder,
        searchQuery: String? = null
    ): Flow<List<ToDo>>
    suspend fun getToDo(id: Int): ToDo?
    suspend fun insertToDo(toDo: ToDo)
    suspend fun updateToDo(toDo: ToDo)
    suspend fun deleteToDo(id: Int)
}