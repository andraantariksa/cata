package id.shaderboi.cata.feature_todo.domain.util

sealed class Order(private val string: String) {
    object Ascending : Order("ASC")
    object Descending : Order("DESC");

    override fun toString(): String {
        return string
    }
}