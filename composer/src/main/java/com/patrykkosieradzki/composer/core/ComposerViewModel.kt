package com.patrykkosieradzki.composer.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class ComposerViewModel<S : ComposerUiStateData>(
    open val initialState: ComposerUiState<S>
) : ViewModel() {
    private val _uiState: MutableStateFlow<ComposerUiState<S>> by lazy {
        MutableStateFlow(initialState)
    }
    val uiState = _uiState.asStateFlow()
    val currentStateData
        get() = uiState.value.getData()

    fun updateUiState(updateFunc: (ComposerUiState<S>) -> ComposerUiState<S>) {
        _uiState.update(updateFunc)
    }

    protected fun updateUiStateToLoading() {
        updateUiState { ComposerUiState.Loading(uiState.value.getData()) }
    }

    protected fun updateUiStateToRetrying() {
        updateUiState { ComposerUiState.Loading(uiState.value.getData()) }
    }

    protected fun updateUiStateToSwipeRefreshing() {
        updateUiState { ComposerUiState.Loading(uiState.value.getData()) }
    }

    protected fun updateUiStateToSuccess(updateFunc: (S) -> S) {
        val newStateData = updateFunc.invoke(uiState.value.getData())
        updateUiState { ComposerUiState.Success(newStateData) }
    }

    protected fun updateUiStateToFailure(
        error: Throwable,
        updateFunc: ((S) -> S)? = null
    ) {
        val newStateData = updateFunc?.invoke(uiState.value.getData()) ?: uiState.value.getData()
        updateUiState { ComposerUiState.Failure(newStateData, error) }
    }

    protected fun updateUiStateToSwipeRefreshFailure(
        error: Throwable,
        updateFunc: ((S) -> S)? = null
    ) {
        val newStateData = updateFunc?.invoke(uiState.value.getData()) ?: uiState.value.getData()
        updateUiState { ComposerUiState.SwipeRefreshFailure(newStateData, error) }
    }

    private val handler = CoroutineExceptionHandler { _, exception ->
        handleError(exception)
    }

    protected open fun handleError(error: Throwable) {
        // Provide default error handling nad override if needed
        updateUiStateToFailure(error)
    }

    protected fun safeLaunch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(handler, block = block)
    }
}