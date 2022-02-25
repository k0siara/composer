package com.patrykkosieradzki.composer.core.state.simple

import com.patrykkosieradzki.composer.core.state.UiState

sealed class SimpleUiState : UiState {
    object Loading : SimpleUiState()
    object Loaded : SimpleUiState()
    data class Error(val throwable: Throwable) : SimpleUiState()
}
