package id.shaderboi.cata.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String = "",
    val description: String = "",
    val priority: ToDoPriority = ToDoPriority.None,
    val checked: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
) {
    constructor(
        title: String,
        content: String,
        priority: ToDoPriority,
    ) : this(0, title, content, priority)

    constructor() : this(0)
}
