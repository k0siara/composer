package com.patrykkosieradzki.composer.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiState
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiStateManager
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState

@Composable
fun SimpleUiStateView(
    simpleUiStateManager: SimpleUiStateManager,
    renderOnLoading: @Composable (() -> Unit)? = null,
    renderOnRetrying: @Composable (() -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable (() -> Unit)? = null,
    renderOnFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable (() -> Unit)? = null
) {
    val state by simpleUiStateManager.uiState.asLifecycleAwareState()

    Crossfade(
        targetState = state,
        animationSpec = tween(300)
    ) {
        when (it) {
            is SimpleUiState.Loading -> renderOnLoading?.invoke()
            is SimpleUiState.Retrying -> renderOnRetrying?.invoke()
            is SimpleUiState.SwipeRefreshing -> renderOnSwipeRefreshing?.invoke()
            is SimpleUiState.Success -> renderOnSuccess?.invoke()
            is SimpleUiState.Failure -> renderOnFailure?.invoke(it.throwable)
            is SimpleUiState.SwipeRefreshFailure -> renderOnSwipeRefreshFailure?.invoke(it.throwable)
        }
    }
}
