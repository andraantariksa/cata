package id.shaderboi.cata.feature_todo.data.repository

import id.shaderboi.cata.feature_todo.data.data_source.ToDoDao
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import kotlinx.coroutines.flow.Flow

class ToDoRepositoryImpl(
    private val dao: ToDoDao
) : ToDoRepository {
    override fun getToDo(
        orderField: ToDoOrder,
        searchQuery: String?
    ): Flow<List<ToDo>> = dao.getToDos(orderField, searchQuery)

    override suspend fun getToDo(id: Int): ToDo? = dao.getToDo(id)

    override suspend fun insertToDo(toDo: ToDo) = dao.insertToDo(toDo)

    override suspend fun updateToDo(toDo: ToDo) = dao.updateToDo(toDo)

    override suspend fun deleteToDo(id: Int) = dao.deleteToDo(id)
}