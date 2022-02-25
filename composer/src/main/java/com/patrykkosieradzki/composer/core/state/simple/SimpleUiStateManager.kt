package com.patrykkosieradzki.composer.core.state.simple

import androidx.lifecycle.SavedStateHandle
import com.patrykkosieradzki.composer.core.state.UiStateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface SimpleUiStateManager : UiStateManager<SimpleUiState>

class SimpleUiStateManagerImpl(
    override val initialState: SimpleUiState,
    private val savedStateHandle: SavedStateHandle
) : SimpleUiStateManager {
    private val _uiState: MutableStateFlow<SimpleUiState> by lazy {
        MutableStateFlow(initialState)
    }
    override val uiState = _uiState.asStateFlow()
    override val currentState: SimpleUiState
        get() = uiState.value

    override fun updateUiStateToLoading(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        _uiState.update { SimpleUiState.Loading }
    }

    override fun updateUiStateToRetrying(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        _uiState.update { SimpleUiState.Loading }
    }

    override fun updateUiStateToSwipeRefreshing(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        _uiState.update { SimpleUiState.Loading }
    }

    override fun updateUiStateToSuccess(doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        _uiState.update { SimpleUiState.Loading }
    }

    override fun updateUiStateToFailure(error: Throwable, doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        _uiState.update { SimpleUiState.Loading }
    }

    override fun updateUiStateToSwipeRefreshFailure(error: Throwable, doBefore: (() -> Unit)?) {
        doBefore?.let { it() }
        _uiState.update { SimpleUiState.Loading }
    }
}
