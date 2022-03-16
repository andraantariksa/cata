package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.repository.NoteRepository

class GetToDoUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(
        id: Int
    ): ToDo? {
        return repository.getToDo(id)
    }
}