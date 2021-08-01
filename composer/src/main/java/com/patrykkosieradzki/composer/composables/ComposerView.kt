package com.patrykkosieradzki.composer.composables

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import com.patrykkosieradzki.composer.*
import com.patrykkosieradzki.composer.Composer.UIStateRenderConfig.Companion.defaultFailureComposable
import com.patrykkosieradzki.composer.Composer.UIStateRenderConfig.Companion.defaultLoadingComposable
import com.patrykkosieradzki.composer.Composer.UIStateRenderConfig.Companion.defaultRetryingComposable
import com.patrykkosieradzki.composer.Composer.UIStateRenderConfig.Companion.defaultSuccessComposable
import com.patrykkosieradzki.composer.Composer.UIStateRenderConfig.Companion.defaultSwipeRefreshFailureComposable
import com.patrykkosieradzki.composer.Composer.UIStateRenderConfig.Companion.defaultSwipeRefreshingComposable
import com.patrykkosieradzki.composer.composables.snackbar.HandleSnackbarIfSupported

@Composable
fun <D : ComposerUIStateData, VM : ComposerViewModel<D>> ComposerView(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    viewModel: VM,
    renderOnLoading: @Composable (() -> Unit)? = null,
    renderOnRetrying: @Composable (() -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable (() -> Unit)? = null,
    renderOnFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable ((data: D) -> Unit)? = null,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state by lifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        stateFlow = viewModel.uiState,
        initialState = viewModel.initialState
    )

    when (state) {
        is ComposerUIState.Loading -> {
            (renderOnLoading.orElse(defaultLoadingComposable))?.invoke()
        }
        is ComposerUIState.Retrying -> {
            (renderOnRetrying.orElse(defaultRetryingComposable))?.invoke()
        }
        is ComposerUIState.SwipeRefreshing -> {
            (renderOnSwipeRefreshing.orElse(defaultSwipeRefreshingComposable))?.invoke()
        }
        is ComposerUIState.Success -> {
            val data = (state as ComposerUIState.Success).data
            (renderOnSuccess.orElse(defaultSuccessComposable))?.invoke(data)
        }
        is ComposerUIState.Failure -> {
            val error = (state as ComposerUIState.Failure).error
            (renderOnFailure.orElse(defaultFailureComposable))?.invoke(error)
        }
        is ComposerUIState.SwipeRefreshFailure -> {
            val error = (state as ComposerUIState.SwipeRefreshFailure).error
            (renderOnSwipeRefreshFailure.orElse(defaultSwipeRefreshFailureComposable))?.invoke(error)
        }
    }

    HandleSnackbarIfSupported(viewModel, scaffoldState)
}
