package com.patrykkosieradzki.composer.core

interface ComposerUiStateData

sealed class ComposerUiState<out T : ComposerUiStateData>(private val value: T) {
    open fun getData(): T = value

    data class Loading<out T : ComposerUiStateData>(val value: T) : ComposerUiState<T>(value)
    data class Retrying<out T : ComposerUiStateData>(val value: T) : ComposerUiState<T>(value)
    data class SwipeRefreshing<out T : ComposerUiStateData>(val value: T) : ComposerUiState<T>(value)
    data class Success<T : ComposerUiStateData>(val value: T) : ComposerUiState<T>(value)
    data class Failure<T : ComposerUiStateData>(val value: T, val error: Throwable) : ComposerUiState<T>(value)
    data class SwipeRefreshFailure<T : ComposerUiStateData>(val value: T, val error: Throwable) :
        ComposerUiState<T>(value)
}

val <T : ComposerUiStateData> ComposerUiState<T>.asLoading: ComposerUiState.Loading<T>
    get() = this as ComposerUiState.Loading<T>

val <T : ComposerUiStateData> ComposerUiState<T>.asRetrying: ComposerUiState.Retrying<T>
    get() = this as ComposerUiState.Retrying<T>

val <T : ComposerUiStateData> ComposerUiState<T>.asSwipeRefreshing: ComposerUiState.SwipeRefreshing<T>
    get() = this as ComposerUiState.SwipeRefreshing<T>

val <T : ComposerUiStateData> ComposerUiState<T>.asSuccess: ComposerUiState.Success<T>
    get() = this as ComposerUiState.Success<T>

val <T : ComposerUiStateData> ComposerUiState<T>.asFailure: ComposerUiState.Failure<T>
    get() = this as ComposerUiState.Failure<T>

val <T : ComposerUiStateData> ComposerUiState<T>.asSwipeRefreshFailure: ComposerUiState.SwipeRefreshFailure<T>
    get() = this as ComposerUiState.SwipeRefreshFailure<T>
