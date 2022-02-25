package com.patrykkosieradzki.composer.core.state.simple

import com.patrykkosieradzki.composer.core.state.UiState

sealed class SimpleUiState : UiState {
    object Loading : SimpleUiState()
    object Retrying : SimpleUiState()
    object SwipeRefreshing : SimpleUiState()
    object Success : SimpleUiState()
    data class Failure(val throwable: Throwable) : SimpleUiState()
    data class SwipeRefreshFailure(val throwable: Throwable) : SimpleUiState()
}
