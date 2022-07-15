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
package com.patrykkosieradzki.composer.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState

@Composable
fun UiStateView(
    uiStateManager: UiStateManager,
    renderOnLoading: @Composable (() -> Unit)? = null,
    renderOnRetrying: @Composable (() -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable (() -> Unit)? = null,
    renderOnFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable (() -> Unit)? = null
) {
    val state by uiStateManager.uiState.asLifecycleAwareState()

    Crossfade(
        targetState = state,
        animationSpec = tween(300)
    ) { uiState ->
        when (uiState) {
            is UiState.Loading -> renderOnLoading?.invoke()
            is UiState.Retrying -> renderOnRetrying?.invoke()
            is UiState.SwipeRefreshing -> renderOnSwipeRefreshing?.invoke()
            is UiState.Success -> renderOnSuccess?.invoke()
            is UiState.Failure -> renderOnFailure?.invoke(uiState.throwable)
            is UiState.SwipeRefreshFailure -> renderOnSwipeRefreshFailure?.invoke(uiState.throwable)
        }
    }
}
