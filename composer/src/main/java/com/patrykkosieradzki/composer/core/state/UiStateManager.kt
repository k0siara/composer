package com.patrykkosieradzki.composer.core.state

import kotlinx.coroutines.flow.StateFlow

interface UiStateManager<STATE> {
    val initialState: STATE
    val uiState: StateFlow<STATE>
    val currentState: STATE

    fun updateUiStateToLoading(doBefore: (() -> Unit)? = null)
    fun updateUiStateToSuccess(doBefore: (() -> Unit)? = null)
    fun updateUiStateToRetrying(doBefore: (() -> Unit)? = null)
    fun updateUiStateToSwipeRefreshing(doBefore: (() -> Unit)? = null)
    fun updateUiStateToFailure(error: Throwable, doBefore: (() -> Unit)? = null)
    fun updateUiStateToSwipeRefreshFailure(error: Throwable, doBefore: (() -> Unit)? = null)
}
