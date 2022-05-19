package com.patrykkosieradzki.composer.core.state

sealed interface UiState {
    object Loading : UiState
    object Success : UiState
    data class Failure(val throwable: Throwable) : UiState
}
