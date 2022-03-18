package id.shaderboi.cata.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    val priority: ToDoPriority,
    val timestamp: Long
) {
    constructor(
        title: String,
        content: String,
        priority: ToDoPriority,
        timestamp: Long
    ) : this(0, title, content, priority, timestamp)
}
