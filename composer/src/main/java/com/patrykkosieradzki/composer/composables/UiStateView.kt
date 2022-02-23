package com.patrykkosieradzki.composer.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.patrykkosieradzki.composer.core.*
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultFailureComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultLoadingComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultRetryingComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultSuccessComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultSwipeRefreshFailureComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultSwipeRefreshingComposable
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState
import com.patrykkosieradzki.composer.utils.lifecycleAwareState

@Composable
fun <DATA : Any> UiStateView(
    uiStateManager: UiStateManager<DATA>,
    renderOnLoading: @Composable ((DATA) -> Unit)? = null,
    renderOnRetrying: @Composable ((DATA) -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable ((DATA) -> Unit)? = null,
    renderOnFailure: @Composable ((DATA, error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((DATA, error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable ((data: DATA) -> Unit)? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by uiStateManager.uiState.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = uiStateManager.uiState.value
    )
    val stateData = state.getData()

    Crossfade(
        targetState = state,
        animationSpec = tween(300)
    ) {
        when (it) {
            is UiState.Loading -> Loading(renderOnLoading, stateData)
            is UiState.Retrying -> Retrying(renderOnRetrying, stateData)
            is UiState.SwipeRefreshing -> SwipeRefreshing(renderOnSwipeRefreshing, stateData)
            is UiState.Success -> Success(renderOnSuccess, stateData)
            is UiState.Failure -> Failure(renderOnFailure, stateData, state.asFailure.error)
            is UiState.SwipeRefreshFailure -> SwipeRefreshFailure(
                renderOnSwipeRefreshFailure,
                stateData,
                state.asSwipeRefreshFailure.error
            )
        }
    }

}

@Composable
private fun <DATA : Any> Loading(
    renderOnLoading: @Composable ((DATA) -> Unit)? = null,
    uiStateData: DATA
) {
    if (renderOnLoading != null) {
        renderOnLoading.invoke(uiStateData)
    } else {
        defaultLoadingComposable.invoke()
    }
}

@Composable
private fun <DATA : Any> Retrying(
    renderOnRetrying: @Composable ((DATA) -> Unit)? = null,
    uiStateData: DATA
) {
    if (renderOnRetrying != null) {
        renderOnRetrying.invoke(uiStateData)
    } else {
        defaultRetryingComposable.invoke()
    }
}

@Composable
private fun <DATA : Any> SwipeRefreshing(
    renderOnSwipeRefreshing: @Composable ((DATA) -> Unit)? = null,
    uiStateData: DATA
) {
    if (renderOnSwipeRefreshing != null) {
        renderOnSwipeRefreshing.invoke(uiStateData)
    } else {
        defaultSwipeRefreshingComposable.invoke()
    }
}

@Composable
private fun <DATA : Any> Success(
    renderOnSuccess: @Composable ((DATA) -> Unit)? = null,
    uiStateData: DATA
) {
    if (renderOnSuccess != null) {
        renderOnSuccess.invoke(uiStateData)
    } else {
        defaultSuccessComposable.invoke()
    }
}

@Composable
private fun <D : Any> Failure(
    renderOnFailure: @Composable ((D, error: Throwable) -> Unit)? = null,
    uiStateData: D,
    error: Throwable
) {
    if (renderOnFailure != null) {
        renderOnFailure.invoke(uiStateData, error)
    } else {
        defaultFailureComposable.invoke(error)
    }
}

@Composable
private fun <D : Any> SwipeRefreshFailure(
    renderOnSwipeRefreshFailure: @Composable ((D, error: Throwable) -> Unit)? = null,
    uiStateData: D,
    error: Throwable
) {
    if (renderOnSwipeRefreshFailure != null) {
        renderOnSwipeRefreshFailure.invoke(uiStateData, error)
    } else {
        defaultSwipeRefreshFailureComposable.invoke(error)
    }
}
