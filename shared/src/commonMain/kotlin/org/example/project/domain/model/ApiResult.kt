package org.example.project.domain.model

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val message: String?, val cause: Throwable? = null) : ApiResult<Nothing>

    fun <R> map(transform: (T) -> R): ApiResult<R> = when (this) {
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

    fun onSuccess(action: (T) -> Unit): ApiResult<T> {
        if (this is Success) action(data)
        return this
    }

    fun onFailure(action: (Failure) -> Unit): ApiResult<T> {
        if (this is Failure) action(this)
        return this
    }
}