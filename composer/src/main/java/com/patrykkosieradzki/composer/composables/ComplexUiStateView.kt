package com.patrykkosieradzki.composer.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultFailureComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultLoadingComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultRetryingComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultSuccessComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultSwipeRefreshFailureComposable
import com.patrykkosieradzki.composer.core.Composer.UiStateRenderConfig.Companion.defaultSwipeRefreshingComposable
import com.patrykkosieradzki.composer.core.state.complex.ComplexUiState
import com.patrykkosieradzki.composer.core.state.complex.ComplexUiStateManager
import com.patrykkosieradzki.composer.core.state.complex.asFailure
import com.patrykkosieradzki.composer.core.state.complex.asSwipeRefreshFailure
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState

@Composable
fun <DATA : Any> ComplexUiStateView(
    complexUiStateManager: ComplexUiStateManager<DATA>,
    renderOnLoading: @Composable ((DATA) -> Unit)? = null,
    renderOnRetrying: @Composable ((DATA) -> Unit)? = null,
    renderOnSwipeRefreshing: @Composable ((DATA) -> Unit)? = null,
    renderOnFailure: @Composable ((DATA, error: Throwable) -> Unit)? = null,
    renderOnSwipeRefreshFailure: @Composable ((DATA, error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable ((data: DATA) -> Unit)? = null
) {
    val state by complexUiStateManager.uiState.asLifecycleAwareState()
    val stateData = state.getData()

    Crossfade(
        targetState = state,
        animationSpec = tween(300)
    ) {
        when (it) {
            is ComplexUiState.Loading -> Loading(renderOnLoading, stateData)
            is ComplexUiState.Retrying -> Retrying(renderOnRetrying, stateData)
            is ComplexUiState.SwipeRefreshing -> SwipeRefreshing(renderOnSwipeRefreshing, stateData)
            is ComplexUiState.Success -> Success(renderOnSuccess, stateData)
            is ComplexUiState.Failure -> Failure(renderOnFailure, stateData, state.asFailure.error)
            is ComplexUiState.SwipeRefreshFailure -> SwipeRefreshFailure(
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
