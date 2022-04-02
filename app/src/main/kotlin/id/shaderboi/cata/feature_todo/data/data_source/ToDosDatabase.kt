package id.shaderboi.cata.feature_todo.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import id.shaderboi.cata.feature_todo.data.data_source.converters.ToDoPriorityConverters
import id.shaderboi.cata.feature_todo.domain.model.ToDo

@Database(
    entities = [ToDo::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ToDoPriorityConverters::class)
abstract class ToDosDatabase : RoomDatabase() {
    abstract val toDoDao: ToDoDao

    companion object {
        const val NAME = "todos"
    }
}