package id.shaderboi.cata.feature_todo.data.data_source

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.model.sorting.ToDoOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @RawQuery(observedEntities = [ToDo::class])
    fun getRawToDosFlow(query: SimpleSQLiteQuery): Flow<List<ToDo>>

    fun getToDos(
        orderField: ToDoOrder,
        searchQuery: String? = null
    ): Flow<List<ToDo>> {
        val args = ArrayList<Any>()
        var query = "SELECT * FROM todo"
        if (searchQuery != null) {
            query += " WHERE title LIKE '%' || :search_query || '%' OR" +
                    " description LIKE '%' || :search_query || '%'"
            args.add(searchQuery)
        }
        query += " ORDER BY ${orderField.toDoOrderField} ${orderField.order}"
        return getRawToDosFlow(SimpleSQLiteQuery(query, args.toArray()))
    }

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getToDo(id: Int): ToDo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDo(toDo: ToDo)

    @Update
    suspend fun updateToDo(toDo: ToDo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteToDo(id: Int)
}