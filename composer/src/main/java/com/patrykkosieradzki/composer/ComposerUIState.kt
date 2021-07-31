package com.patrykkosieradzki.composer

sealed class ComposerUIState<out T : ComposerUIStateData> {
    object Loading : ComposerUIState<Nothing>()
    object Retrying : ComposerUIState<Nothing>()
    object SwipeRefreshing : ComposerUIState<Nothing>()
    data class Success<T : ComposerUIStateData>(val data: T) : ComposerUIState<T>()
    data class Failure(val exception: Exception) : ComposerUIState<Nothing>()
    data class SwipeRefreshFailure(val exception: Exception) : ComposerUIState<Nothing>()
}

interface ComposerUIStateData