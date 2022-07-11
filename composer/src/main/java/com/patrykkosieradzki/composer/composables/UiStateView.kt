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
import com.patrykkosieradzki.composer.core.Composer
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState

@Composable
fun UiStateView(
    uiStateManager: UiStateManager,
    renderOnLoading: @Composable (() -> Unit)? = null,
    renderOnFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable (() -> Unit)? = null
) {
    val state by uiStateManager.uiState.asLifecycleAwareState()

    Crossfade(
        targetState = state,
        animationSpec = tween(300)
    ) {
        when (it) {
            is UiState.Loading -> Loading(renderOnLoading)
            is UiState.Success -> Success(renderOnSuccess)
            is UiState.Failure -> Failure(renderOnFailure, it.throwable)
        }
    }
}

@Composable
private fun Loading(
    renderOnLoading: @Composable (() -> Unit)? = null
) {
    if (renderOnLoading != null) {
        renderOnLoading.invoke()
    } else {
        Composer.UiStateRenderConfig.defaultLoadingComposable.invoke()
    }
}

@Composable
private fun Success(
    renderOnSuccess: @Composable (() -> Unit)? = null
) {
    if (renderOnSuccess != null) {
        renderOnSuccess.invoke()
    } else {
        Composer.UiStateRenderConfig.defaultSuccessComposable.invoke()
    }
}

@Composable
private fun Failure(
    renderOnFailure: @Composable ((error: Throwable) -> Unit)? = null,
    error: Throwable
) {
    if (renderOnFailure != null) {
        renderOnFailure.invoke(error)
    } else {
        Composer.UiStateRenderConfig.defaultFailureComposable.invoke(error)
    }
}
