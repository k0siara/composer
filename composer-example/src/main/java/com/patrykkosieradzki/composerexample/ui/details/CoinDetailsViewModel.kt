package com.patrykkosieradzki.composerexample.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiState
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiStateManager
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiStateManagerImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(),
    SimpleUiStateManager by SimpleUiStateManagerImpl(SimpleUiState.Loaded, savedStateHandle) {
}