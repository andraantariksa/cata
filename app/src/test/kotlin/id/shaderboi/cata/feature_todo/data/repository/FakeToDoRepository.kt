package id.shaderboi.cata.feature_todo.data.repository

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrderField
import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeToDoRepository : ToDoRepository {
    private val toDos = mutableListOf<ToDo>()
    private var currentId = 1

    override fun getToDo(orderField: ToDoOrder, searchQuery: String?): Flow<List<ToDo>> {
        return flow {
            toDos
                .filter {
                    searchQuery ?: return@filter true
                    it.title.contains(searchQuery) || it.description.contains(searchQuery)
                }
                .sortedWith(compareBy {
                    when (orderField.toDoOrderField) {
                        ToDoOrderField.Date -> it.updatedAt
                        ToDoOrderField.Priority -> it.description
                        ToDoOrderField.Title -> it.title
                    }
                })
        }
    }

    override suspend fun getToDo(id: Int): ToDo? {
        return toDos.find { it.id == id }
    }

    override suspend fun insertToDo(_toDo: ToDo) {
        val toDo = if (_toDo.id == 0) {
            val ret = _toDo.copy(id = currentId)
            currentId += 1
            ret
        } else {
            _toDo
        }
        toDos.add(toDo)
    }

    override suspend fun updateToDo(_toDo: ToDo) {
        val toDoIdx = toDos.indexOfFirst { it.id == _toDo.id }
        toDos[toDoIdx] = _toDo
    }

    override suspend fun deleteToDo(id: Int) {
        toDos.removeIf { it.id == id }
    }
}