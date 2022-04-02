package id.shaderboi.cata.feature_todo.domain.model.sorting

sealed class ToDoOrderField(private val fieldName: String) {
    object Title : ToDoOrderField("title")
    object Date : ToDoOrderField("updatedAt")
    object Priority : ToDoOrderField("priority")

    override fun toString(): String {
        return fieldName
    }
}