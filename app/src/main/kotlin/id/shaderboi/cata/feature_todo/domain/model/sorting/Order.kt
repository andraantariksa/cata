package id.shaderboi.cata.feature_todo.domain.model.sorting

sealed class Order(private val string: String) {
    object Ascending : Order("ASC")
    object Descending : Order("DESC")

    override fun toString(): String {
        return string
    }
}