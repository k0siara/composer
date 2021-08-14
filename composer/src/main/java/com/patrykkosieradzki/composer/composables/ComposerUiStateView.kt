package com.patrykkosieradzki.composer.composables

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
import com.patrykkosieradzki.composer.utils.lifecycleAwareState

@Composable
fun <D : ComposerUiStateData, VM : ComposerViewModel<D>> ComposerUiStateView(
    viewModel: VM,
    renderOnLoading: @Composable ((D) -> Unit)? = null,
    renderOnRetrying: @Composable ((D) -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable ((D) -> Unit)? = null,
    renderOnFailure: @Composable ((D, error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((D, error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable ((data: D) -> Unit)? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by lifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        flow = viewModel.uiState,
        initialState = viewModel.initialState
    )
    val stateData = state.getData()

    when (state) {
        is ComposerUiState.Loading -> Loading(renderOnLoading, stateData)
        is ComposerUiState.Retrying -> Retrying(renderOnRetrying, stateData)
        is ComposerUiState.SwipeRefreshing -> SwipeRefreshing(renderOnSwipeRefreshing, stateData)
        is ComposerUiState.Success -> Success(renderOnSuccess, stateData)
        is ComposerUiState.Failure -> Failure(renderOnFailure, stateData, state.asFailure.error)
        is ComposerUiState.SwipeRefreshFailure -> SwipeRefreshFailure(
            renderOnSwipeRefreshFailure,
            stateData,
            state.asSwipeRefreshFailure.error
        )
    }
}

@Composable
private fun <D : ComposerUiStateData> Loading(
    renderOnLoading: @Composable ((D) -> Unit)? = null,
    uiStateData: D
) {
    if (renderOnLoading != null) {
        renderOnLoading.invoke(uiStateData)
    } else {
        defaultLoadingComposable.invoke()
    }
}

@Composable
private fun <D : ComposerUiStateData> Retrying(
    renderOnRetrying: @Composable ((D) -> Unit)? = null,
    uiStateData: D
) {
    if (renderOnRetrying != null) {
        renderOnRetrying.invoke(uiStateData)
    } else {
        defaultRetryingComposable.invoke()
    }
}

@Composable
private fun <D : ComposerUiStateData> SwipeRefreshing(
    renderOnSwipeRefreshing: @Composable ((D) -> Unit)? = null,
    uiStateData: D
) {
    if (renderOnSwipeRefreshing != null) {
        renderOnSwipeRefreshing.invoke(uiStateData)
    } else {
        defaultSwipeRefreshingComposable.invoke()
    }
}

@Composable
private fun <D : ComposerUiStateData> Success(
    renderOnSuccess: @Composable ((D) -> Unit)? = null,
    uiStateData: D
) {
    if (renderOnSuccess != null) {
        renderOnSuccess.invoke(uiStateData)
    } else {
        defaultSuccessComposable.invoke()
    }
}

@Composable
private fun <D : ComposerUiStateData> Failure(
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
private fun <D : ComposerUiStateData> SwipeRefreshFailure(
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
