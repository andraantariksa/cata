package id.shaderboi.cata.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ToDo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String,
    val content: String,
    val color: Int,
    val timestamp: Long
) {
    constructor(
        title: String,
        content: String,
        color: Int,
        timestamp: Long
    ) : this(0, title, content, color, timestamp)
}
