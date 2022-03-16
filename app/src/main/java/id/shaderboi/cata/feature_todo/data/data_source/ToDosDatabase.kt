package id.shaderboi.cata.feature_todo.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import id.shaderboi.cata.feature_todo.domain.model.ToDo

@Database(
    entities = [ToDo::class],
    version = 1,
    exportSchema = false
)
abstract class ToDosDatabase : RoomDatabase() {
    abstract val toDoDao: ToDoDao

    companion object {
        const val NAME = "todos"
    }
}