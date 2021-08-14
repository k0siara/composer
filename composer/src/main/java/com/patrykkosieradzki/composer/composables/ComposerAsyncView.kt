package com.patrykkosieradzki.composer.composables

import androidx.compose.runtime.Composable
import com.patrykkosieradzki.composer.core.ComposerAsync
import com.patrykkosieradzki.composer.core.Composer
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultFailComposable
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultLoadingComposable
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultSuccessComposable
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultUninitializedComposable
import com.patrykkosieradzki.composer.utils.orElse

@Composable
fun <T> ComposerAsyncView(
    composerAsync: ComposerAsync<T>,
    hideOnFailure: Boolean = false,
    renderOnUninitialized: @Composable (() -> Unit)? = null,
    renderOnLoading: @Composable ((data: T?) -> Unit)? = null,
    renderOnFail: @Composable ((data: T?, error: Throwable) -> Unit)? = null,
    renderOnSuccess: @Composable ((data: T?) -> Unit)? = null,
) {
    when (composerAsync) {
        is ComposerAsync.Uninitialized -> {
            (renderOnUninitialized.orElse(defaultUninitializedComposable))?.invoke()
        }
        is ComposerAsync.Loading -> {
            if (renderOnLoading != null) {
                renderOnLoading.invoke(composerAsync())
            } else {
                defaultLoadingComposable.invoke()
            }
        }
        is ComposerAsync.Success -> {
            if (renderOnSuccess != null) {
                renderOnSuccess.invoke(composerAsync())
            } else {
                defaultSuccessComposable.invoke()
            }
        }
        is ComposerAsync.Fail -> {
            if (!Composer.AsyncRenderConfig.hideEveryAsyncOnFailure && !hideOnFailure) {
                if (renderOnFail != null) {
                    renderOnFail.invoke(composerAsync(), composerAsync.error)
                } else {
                    defaultFailComposable.invoke(composerAsync.error)
                }
            }
        }
    }
}