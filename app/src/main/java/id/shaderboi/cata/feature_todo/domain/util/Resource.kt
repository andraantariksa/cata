package id.shaderboi.cata.feature_todo.domain.util

sealed class Resource<T, E> {
    class Loading<T, E>: Resource<T, E>()
    class Loaded<T, E>(var data: T): Resource<T, E>()
    class Error<T, E>(var error: E): Resource<T, E>()
}
