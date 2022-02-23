package com.patrykkosieradzki.composer.core

sealed class UiState<out T : Any>(private val value: T) {
    open fun getData(): T = value

    data class Loading<out T : Any>(val value: T) : UiState<T>(value)
    data class Retrying<out T : Any>(val value: T) : UiState<T>(value)
    data class SwipeRefreshing<out T : Any>(val value: T) : UiState<T>(value)
    data class Success<T : Any>(val value: T) : UiState<T>(value)
    data class Failure<T : Any>(val value: T, val error: Throwable) : UiState<T>(value)
    data class SwipeRefreshFailure<T : Any>(val value: T, val error: Throwable) :
        UiState<T>(value)
}

val <T : Any> UiState<T>.asLoading: UiState.Loading<T>
    get() = this as UiState.Loading<T>

val <T : Any> UiState<T>.asRetrying: UiState.Retrying<T>
    get() = this as UiState.Retrying<T>

val <T : Any> UiState<T>.asSwipeRefreshing: UiState.SwipeRefreshing<T>
    get() = this as UiState.SwipeRefreshing<T>

val <T : Any> UiState<T>.asSuccess: UiState.Success<T>
    get() = this as UiState.Success<T>

val <T : Any> UiState<T>.asFailure: UiState.Failure<T>
    get() = this as UiState.Failure<T>

val <T : Any> UiState<T>.asSwipeRefreshFailure: UiState.SwipeRefreshFailure<T>
    get() = this as UiState.SwipeRefreshFailure<T>
