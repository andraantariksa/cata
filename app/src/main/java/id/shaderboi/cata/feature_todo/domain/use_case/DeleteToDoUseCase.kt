package id.shaderboi.cata.feature_todo.domain.use_case

import id.shaderboi.cata.feature_todo.domain.repository.NoteRepository

class DeleteToDoUseCase(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteToDo(id)
    }
}