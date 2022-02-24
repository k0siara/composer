package com.patrykkosieradzki.composer.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface UiStateManager<DATA : Any> {
    val initialState: UiState<DATA>
    val uiState: StateFlow<UiState<DATA>>
    val currentStateData: DATA

    fun updateUiState(updateFunc: (UiState<DATA>) -> UiState<DATA>)
    fun updateUiStateToLoading()
    fun updateUiStateToRetrying()
    fun updateUiStateToSwipeRefreshing()
    fun updateUiStateToSuccess(updateFunc: (DATA) -> DATA)
    fun updateUiStateToFailure(error: Throwable, updateFunc: ((DATA) -> DATA)? = null)
    fun updateUiStateToSwipeRefreshFailure(error: Throwable, updateFunc: ((DATA) -> DATA)? = null)
}

class UiStateManagerImpl<DATA : Any>(
    override val initialState: UiState<DATA>
) : UiStateManager<DATA> {
    private val _uiState: MutableStateFlow<UiState<DATA>> by lazy {
        MutableStateFlow(initialState)
    }
    override val uiState = _uiState.asStateFlow()
    override val currentStateData
        get() = uiState.value.getData()

    override fun updateUiState(updateFunc: (UiState<DATA>) -> UiState<DATA>) {
        _uiState.update(updateFunc)
    }

    override fun updateUiStateToLoading() {
        updateUiState { UiState.Loading(uiState.value.getData()) }
    }

    override fun updateUiStateToRetrying() {
        updateUiState { UiState.Loading(uiState.value.getData()) }
    }

    override fun updateUiStateToSwipeRefreshing() {
        updateUiState { UiState.Loading(uiState.value.getData()) }
    }

    override fun updateUiStateToSuccess(updateFunc: (DATA) -> DATA) {
        val newStateData = updateFunc.invoke(uiState.value.getData())
        updateUiState { UiState.Success(newStateData) }
    }

    override fun updateUiStateToFailure(
        error: Throwable,
        updateFunc: ((DATA) -> DATA)?
    ) {
        val newStateData = updateFunc?.invoke(uiState.value.getData()) ?: uiState.value.getData()
        updateUiState { UiState.Failure(newStateData, error) }
    }

    override fun updateUiStateToSwipeRefreshFailure(
        error: Throwable,
        updateFunc: ((DATA) -> DATA)?
    ) {
        val newStateData = updateFunc?.invoke(uiState.value.getData()) ?: uiState.value.getData()
        updateUiState { UiState.SwipeRefreshFailure(newStateData, error) }
    }
}