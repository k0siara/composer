package com.patrykkosieradzki.composer.core.state.simple

import androidx.lifecycle.SavedStateHandle
import com.patrykkosieradzki.composer.core.state.UiStateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface SimpleUiStateManager : UiStateManager<SimpleUiState>

class SimpleUiStateManagerImpl(
    override val initialState: SimpleUiState,
    private val savedStateHandle: SavedStateHandle? = null
) : SimpleUiStateManager {
    private val _uiState: MutableStateFlow<SimpleUiState> by lazy {
        MutableStateFlow(initialState)
    }
    override val uiState = _uiState.asStateFlow()
    override val currentState: SimpleUiState
        get() = uiState.value

    override fun updateUiStateToLoading(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        update(SimpleUiState.Loading)
    }

    override fun updateUiStateToRetrying(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        update(SimpleUiState.Retrying)
    }

    override fun updateUiStateToSwipeRefreshing(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        update(SimpleUiState.SwipeRefreshing)
    }

    override fun updateUiStateToSuccess(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        update(SimpleUiState.Success, saveForRestoration = true)
    }

    override fun updateUiStateToFailure(error: Throwable, doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        update(SimpleUiState.Failure(error), saveForRestoration = true)
    }

    override fun updateUiStateToSwipeRefreshFailure(error: Throwable, doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        update(SimpleUiState.SwipeRefreshFailure(error), saveForRestoration = true)
    }

    private fun update(state: SimpleUiState, saveForRestoration: Boolean = false) {
        if (saveForRestoration) {
            saveLastNonLoadingState(state)
        }
        _uiState.update { state }
    }

    private fun saveLastNonLoadingState(state: SimpleUiState) {
        savedStateHandle?.set("state", state)
    }
}
