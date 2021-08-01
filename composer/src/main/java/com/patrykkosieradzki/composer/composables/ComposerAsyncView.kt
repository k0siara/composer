package com.patrykkosieradzki.composer.composables

import androidx.compose.runtime.Composable
import com.patrykkosieradzki.composer.Async
import com.patrykkosieradzki.composer.Composer
import com.patrykkosieradzki.composer.Composer.AsyncRenderConfig.Companion.defaultFailComposable
import com.patrykkosieradzki.composer.Composer.AsyncRenderConfig.Companion.defaultLoadingComposable
import com.patrykkosieradzki.composer.Composer.AsyncRenderConfig.Companion.defaultSuccessComposable
import com.patrykkosieradzki.composer.Composer.AsyncRenderConfig.Companion.defaultUninitializedComposable
import com.patrykkosieradzki.composer.orElse

@Composable
fun <T> ComposerAsyncView(
    async: Async<T>,
    hideOnFailure: Boolean = false,
    renderOnUninitialized: @Composable (() -> Unit)? = null,
    renderOnLoading: @Composable ((data: T?) -> Unit)? = null,
    renderOnFail: @Composable ((data: T?, error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable ((data: T?) -> Unit)? = null,
) {
    when (async) {
        is Async.Uninitialized -> {
            (renderOnUninitialized.orElse(defaultUninitializedComposable))?.invoke()
        }
        is Async.Loading -> {
            if (renderOnLoading != null) {
                renderOnLoading.invoke(async())
            } else {
                defaultLoadingComposable.invoke()
            }
        }
        is Async.Success -> {
            if (renderOnSuccess != null) {
                renderOnSuccess.invoke(async())
            } else {
                defaultSuccessComposable.invoke()
            }
        }
        is Async.Fail -> {
            if (!Composer.AsyncRenderConfig.hideEveryAsyncOnFailure && !hideOnFailure) {
                if (renderOnFail != null) {
                    renderOnFail.invoke(async(), async.error)
                } else {
                    defaultFailComposable.invoke(async.error)
                }
            }
        }
    }
}