/*
 * Copyright (C) 2022 Patryk Kosieradzki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patrykkosieradzki.composer.core.state.impl

import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class UiStateManagerImpl(
    override val initialState: UiState
) : UiStateManager {
    private val _uiState: MutableStateFlow<UiState> by lazy {
        MutableStateFlow(initialState)
    }
    override val uiState = _uiState.asStateFlow()
    override val currentState: UiState
        get() = uiState.value

    override fun updateUiStateTo(state: UiState) {
        _uiState.update { state }
    }

    override fun updateUiStateToLoading() {
        updateUiStateTo(UiState.Loading)
    }

    override fun updateUiStateToRetrying() {
        updateUiStateTo(UiState.Retrying)
    }

    override fun updateUiStateToSwipeRefreshing() {
        updateUiStateTo(UiState.SwipeRefreshing)
    }

    override fun updateUiStateToSuccess() {
        updateUiStateTo(UiState.Success)
    }

    override fun updateUiStateToFailure(throwable: Throwable) {
        updateUiStateTo(UiState.Failure(throwable))
    }

    override fun updateUiStateToSwipeRefreshFailure(throwable: Throwable) {
        updateUiStateTo(UiState.SwipeRefreshFailure(throwable))
    }
}
