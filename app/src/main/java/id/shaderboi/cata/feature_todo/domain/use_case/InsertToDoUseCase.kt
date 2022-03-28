package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.exception.InvalidNoteException
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.ToDoRepository

class InsertToDoUseCase(
    private val repository: ToDoRepository
) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(
        toDo: ToDo
    ) {
        if (toDo.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty")
        }
        repository.insertToDo(toDo)
    }
}