package id.shaderboi.cata.feature_todo.domain.util

sealed class Resource<T, E> {
    class Loading<T, E>() : Resource<T, E>()
    data class Loaded<T, E>(val data: T) : Resource<T, E>()
    data class Error<T, E>(val error: E) : Resource<T, E>()
}
