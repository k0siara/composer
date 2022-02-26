package com.patrykkosieradzki.composer.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.patrykkosieradzki.composer.core.Composer
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
            is SimpleUiState.Loading -> Loading(renderOnLoading)
            is SimpleUiState.Retrying -> Retrying(renderOnRetrying)
            is SimpleUiState.SwipeRefreshing -> SwipeRefreshing(renderOnSwipeRefreshing)
            is SimpleUiState.Success -> Success(renderOnSuccess)
            is SimpleUiState.Failure -> Failure(renderOnFailure, it.throwable)
            is SimpleUiState.SwipeRefreshFailure -> SwipeRefreshFailure(renderOnSwipeRefreshFailure, it.throwable)
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
private fun Retrying(
    renderOnRetrying: @Composable (() -> Unit)? = null
) {
    if (renderOnRetrying != null) {
        renderOnRetrying.invoke()
    } else {
        Composer.UiStateRenderConfig.defaultRetryingComposable.invoke()
    }
}

@Composable
private fun SwipeRefreshing(
    renderOnSwipeRefreshing: @Composable (() -> Unit)? = null
) {
    if (renderOnSwipeRefreshing != null) {
        renderOnSwipeRefreshing.invoke()
    } else {
        Composer.UiStateRenderConfig.defaultSwipeRefreshingComposable.invoke()
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

@Composable
private fun SwipeRefreshFailure(
    renderOnSwipeRefreshFailure: @Composable ((error: Throwable) -> Unit)? = null,
    error: Throwable
) {
    if (renderOnSwipeRefreshFailure != null) {
        renderOnSwipeRefreshFailure.invoke(error)
    } else {
        Composer.UiStateRenderConfig.defaultSwipeRefreshFailureComposable.invoke(error)
    }
}
