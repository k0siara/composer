package com.patrykkosieradzki.composer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class ComposerViewModel<S : ComposerUIStateData>(
    open val initialState: ComposerUIState<S> = ComposerUIState.Loading,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ComposerUIState<S>> by lazy {
        MutableStateFlow(initialState)
    }
    val uiState = _uiState.asStateFlow()

    fun updateUiState(updateFunc: (ComposerUIState<S>) -> ComposerUIState<S>) {
        _uiState.update(updateFunc)
    }
}