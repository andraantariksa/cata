package id.shaderboi.cata.feature_todo.data.data_source

import androidx.room.*
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @Query("SELECT * FROM todo")
    fun getNotes(): Flow<List<ToDo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getNote(id: Int): ToDo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(toDo: ToDo)

    @Update()
    suspend fun updateNote(toDo: ToDo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteNote(id: Int)
}