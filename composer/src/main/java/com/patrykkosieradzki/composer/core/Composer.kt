package com.patrykkosieradzki.composer.core

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.patrykkosieradzki.composer.composables.CenteredColumn

class Composer private constructor() {
    class UiStateRenderConfig {
        companion object {
            var defaultLoadingComposable: @Composable () -> Unit = {
                DefaultLoadingView()
            }
            var defaultRetryingComposable: @Composable () -> Unit = {
                DefaultRetryingView()
            }
            var defaultSwipeRefreshingComposable: @Composable () -> Unit = {
                DefaultSwipeRefreshingView()
            }
            var defaultSuccessComposable: @Composable () -> Unit = {
                DefaultSuccessView()
            }
            var defaultFailureComposable: @Composable (error: Throwable) -> Unit = { error ->
                DefaultFailureView(error)
            }
            var defaultSwipeRefreshFailureComposable:
                    @Composable (error: Throwable) -> Unit = { error ->
                DefaultSwipeRefreshFailureView(error)
            }
        }
    }

    class AsyncRenderConfig {
        companion object {
            var defaultUninitializedComposable: @Composable () -> Unit = {
                DefaultUninitializedView()
            }
            var defaultLoadingComposable: @Composable () -> Unit = {
                DefaultLoadingView()
            }
            var defaultSuccessComposable: @Composable () -> Unit = {
                DefaultSuccessView()
            }
            var defaultFailComposable: @Composable (error: Throwable) -> Unit = { error ->
                DefaultFailureView(error)
            }
            var hideEveryAsyncOnFailure = false
        }
    }
}

@Composable
private fun DefaultRetryingView() {
    CenteredColumn {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
        Text(text = "Retrying...")
    }
}

@Composable
private fun DefaultSwipeRefreshingView() {
    CenteredColumn {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
        Text(text = "Swipe refreshing...")
    }
}

@Composable
private fun DefaultSwipeRefreshFailureView(error: Throwable) {
    CenteredColumn {
        Text(text = "Swipe refresh error occurred")
        Text(text = error.message ?: "No message available")
    }
}

@Composable
private fun DefaultUninitializedView() {
    CenteredColumn {
        Text(text = "Uninitialized")
    }
}

@Composable
private fun DefaultLoadingView() {
    CenteredColumn {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
        Text(text = "Loading...")
    }
}

@Composable
private fun DefaultSuccessView() {
    CenteredColumn {
        Text(text = "Success state. To be implemented...")
    }
}

@Composable
private fun DefaultFailureView(error: Throwable) {
    CenteredColumn {
        Text(text = "Error occurred")
        Text(text = error.message ?: "No message available")
    }
}