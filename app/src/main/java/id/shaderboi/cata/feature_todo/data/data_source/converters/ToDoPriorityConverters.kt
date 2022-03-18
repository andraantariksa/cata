package id.shaderboi.cata.feature_todo.data.data_source.converters

import androidx.room.TypeConverter
import id.shaderboi.cata.feature_todo.domain.model.ToDoPriority

class ToDoPriorityConverters {
    @TypeConverter
    fun toToDoPriority(value: Int) = enumValues<ToDoPriority>()[value]

    @TypeConverter
    fun fromToDoPriority(value: ToDoPriority) = value.ordinal
}