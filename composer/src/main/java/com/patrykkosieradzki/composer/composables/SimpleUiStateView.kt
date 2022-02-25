package com.patrykkosieradzki.composer.composables

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiState
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiStateManager
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SimpleUiStateView(
    simpleUiStateManager: SimpleUiStateManager,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    renderOnLoading: @Composable (() -> Unit)? = null,
    renderOnRetrying: @Composable (() -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable (() -> Unit)? = null,
    renderOnFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable (() -> Unit)? = null
) {
    val state by simpleUiStateManager.uiState.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = simpleUiStateManager.uiState.value
    )

    Crossfade(
        targetState = state,
        animationSpec = tween(300)
    ) {
        when (it) {
            is SimpleUiState.Loading -> renderOnLoading?.invoke()
            is SimpleUiState.Loaded -> renderOnSuccess?.invoke()
            is SimpleUiState.Error -> renderOnFailure?.invoke(it.throwable)
        }
    }
}