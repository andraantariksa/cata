package id.shaderboi.cata.feature_todo.domain.repository

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getToDo(): Flow<List<ToDo>>
    suspend fun getToDo(id: Int): ToDo?
    suspend fun insertToDo(toDo: ToDo)
    suspend fun updateToDo(toDo: ToDo)
    suspend fun deleteToDo(id: Int)
}