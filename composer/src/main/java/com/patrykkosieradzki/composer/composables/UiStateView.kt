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
