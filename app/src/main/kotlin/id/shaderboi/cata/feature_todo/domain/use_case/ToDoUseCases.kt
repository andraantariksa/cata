package id.shaderboi.cata.feature_todo.domain.use_case

data class ToDoUseCases(
    val getToDos: GetToDosUseCase,
    val getToDo: GetToDoUseCase,
    val updateToDo: UpdateToDoUseCase,
    val deleteNotes: DeleteToDoUseCase,
    val insertToDo: InsertToDoUseCase
)