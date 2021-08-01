package com.patrykkosieradzki.composer

import androidx.compose.runtime.Composable

class Composer private constructor() {
    class UIStateRenderConfig {
        companion object {
            var defaultLoadingComposable: @Composable () -> Unit = {}
            var defaultRetryingComposable: @Composable () -> Unit = {}
            var defaultSwipeRefreshingComposable: @Composable () -> Unit = {}
            var defaultSuccessComposable: @Composable (data : ComposerUIStateData) -> Unit = {}
            var defaultFailureComposable: @Composable (error: Throwable) -> Unit = {}
            var defaultSwipeRefreshFailureComposable: @Composable (error: Throwable) -> Unit = {}
        }
    }

    class AsyncRenderConfig {
        companion object {
            var defaultUninitializedComposable: @Composable () -> Unit = {}
            var defaultLoadingComposable: @Composable () -> Unit = {}
            var defaultSuccessComposable: @Composable () -> Unit = {}
            var defaultFailComposable: @Composable (error: Throwable) -> Unit = {}
            var hideEveryAsyncOnFailure = false
        }
    }
}