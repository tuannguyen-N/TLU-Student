package org.example.project.domain.model

sealed interface AppResult<out T> {
    data class Success<T>(val data: T) : AppResult<T>
    data class Failure(val message: String?, val cause: Throwable? = null) : AppResult<Nothing>

    fun <R> map(transform: (T) -> R): AppResult<R> = when (this) {
        is Success -> Success(transform(data))
        is Failure -> this
    }

    suspend fun <R> fold(
        onSuccess: suspend (T) -> R,
        onFailure: suspend (Failure) -> R
    ): R = when (this) {
        is Success -> onSuccess(data)
        is Failure -> onFailure(this)
    }

    fun onSuccess(action: (T) -> Unit): AppResult<T> {
        if (this is Success) action(data)
        return this
    }

    fun onFailure(action: (Failure) -> Unit): AppResult<T> {
        if (this is Failure) action(this)
        return this
    }
}