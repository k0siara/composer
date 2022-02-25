package com.patrykkosieradzki.composer.core.state.complex

import com.patrykkosieradzki.composer.core.state.UiState

sealed class ComplexUiState<out T : Any>(private val value: T) : UiState {
    open fun getData(): T = value

    data class Loading<out T : Any>(val value: T) : ComplexUiState<T>(value)
    data class Retrying<out T : Any>(val value: T) : ComplexUiState<T>(value)
    data class SwipeRefreshing<out T : Any>(val value: T) : ComplexUiState<T>(value)
    data class Success<T : Any>(val value: T) : ComplexUiState<T>(value)
    data class Failure<T : Any>(val value: T, val error: Throwable) : ComplexUiState<T>(value)
    data class SwipeRefreshFailure<T : Any>(val value: T, val error: Throwable) :
        ComplexUiState<T>(value)
}

val <T : Any> ComplexUiState<T>.asLoading: ComplexUiState.Loading<T>
    get() = this as ComplexUiState.Loading<T>

val <T : Any> ComplexUiState<T>.asRetrying: ComplexUiState.Retrying<T>
    get() = this as ComplexUiState.Retrying<T>

val <T : Any> ComplexUiState<T>.asSwipeRefreshing: ComplexUiState.SwipeRefreshing<T>
    get() = this as ComplexUiState.SwipeRefreshing<T>

val <T : Any> ComplexUiState<T>.asSuccess: ComplexUiState.Success<T>
    get() = this as ComplexUiState.Success<T>

val <T : Any> ComplexUiState<T>.asFailure: ComplexUiState.Failure<T>
    get() = this as ComplexUiState.Failure<T>

val <T : Any> ComplexUiState<T>.asSwipeRefreshFailure: ComplexUiState.SwipeRefreshFailure<T>
    get() = this as ComplexUiState.SwipeRefreshFailure<T>
