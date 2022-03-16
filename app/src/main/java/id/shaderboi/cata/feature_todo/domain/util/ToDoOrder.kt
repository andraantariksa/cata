package id.shaderboi.cata.feature_todo.domain.util

sealed class ToDoOrder(val order: Order) {
    class Title(order: Order): ToDoOrder(order)
    class Date(order: Order): ToDoOrder(order)
}