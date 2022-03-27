package id.shaderboi.cata.feature_todo.data.data_source

import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import id.shaderboi.cata.feature_todo.domain.model.ToDo
import id.shaderboi.cata.feature_todo.domain.util.Order
import id.shaderboi.cata.feature_todo.domain.util.ToDoOrder
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {
    @RawQuery(observedEntities = [ToDo::class])
    fun getRawNotesFlow(query: SimpleSQLiteQuery): Flow<List<ToDo>>

    fun getNotes(
        order: ToDoOrder = ToDoOrder.Date(Order.Descending),
        searchQuery: String? = null
    ): Flow<List<ToDo>> {
        val args = ArrayList<Any>()
        var query = "SELECT * FROM todo"
        if (searchQuery != null) {
            query += " WHERE title LIKE '%' || :search_query || '%' OR" +
                    " content LIKE '%' || :search_query || '%'"
            args.add(searchQuery)
//            args.add(searchQuery)
        }
        query += " ORDER BY ${order.fieldName} ${order.order}"
        return getRawNotesFlow(SimpleSQLiteQuery(query, args.toArray()))
    }

    @Query("SELECT * FROM todo WHERE id = :id")
    suspend fun getNote(id: Int): ToDo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(toDo: ToDo)

    @Update
    suspend fun updateNote(toDo: ToDo)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteNote(id: Int)
}