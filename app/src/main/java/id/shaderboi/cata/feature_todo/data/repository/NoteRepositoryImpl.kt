package id.shaderboi.cata.feature_todo.data.repository

import id.shaderboi.cata.feature_todo.data.data_source.ToDoDao
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.NoteRepository
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: ToDoDao
) : NoteRepository {
    override fun getToDo(
        order: ToDoOrder,
        searchQuery: String?
    ): Flow<List<ToDo>> = dao.getNotes(order, searchQuery)

    override suspend fun getToDo(id: Int): ToDo? = dao.getNote(id)

    override suspend fun insertToDo(toDo: ToDo) = dao.insertNote(toDo)

    override suspend fun updateToDo(toDo: ToDo) = dao.updateNote(toDo)

    override suspend fun deleteToDo(id: Int) = dao.deleteNote(id)
}