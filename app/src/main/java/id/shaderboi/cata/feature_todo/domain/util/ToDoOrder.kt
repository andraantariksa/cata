package id.shaderboi.cata.feature_todo.domain.util

sealed class ToDoOrder(val order: Order, val fieldName: String) {
    class Title(order: Order) : ToDoOrder(order, "title")
    class Date(order: Order) : ToDoOrder(order, "updatedAt")
    class Priority(order: Order) : ToDoOrder(order, "priority")
}