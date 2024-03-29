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
package com.patrykkosieradzki.composer.core.state

import com.patrykkosieradzki.composer.core.state.impl.UiStateManagerImpl
import kotlinx.coroutines.flow.StateFlow

interface UiStateManager {
    val initialState: UiState
    val uiState: StateFlow<UiState>
    val currentState: UiState

    fun updateUiStateTo(uiState: UiState)
    fun updateUiStateToLoading()
    fun updateUiStateToSuccess()
    fun updateUiStateToRetrying()
    fun updateUiStateToSwipeRefreshing()
    fun updateUiStateToFailure(throwable: Throwable)
    fun updateUiStateToSwipeRefreshFailure(throwable: Throwable)

    companion object {
        fun delegate(
            initialState: UiState
        ): UiStateManager = UiStateManagerImpl(initialState = initialState)
    }
}
