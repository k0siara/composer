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
        is ComposerAsync.Uninitialized -> Uninitialized(renderOnUninitialized)
        is ComposerAsync.Loading -> Loading(renderOnLoading, composerAsync)
        is ComposerAsync.Success -> Success(renderOnSuccess, composerAsync)
        is ComposerAsync.Fail -> Fail(renderOnFail, composerAsync, hideOnFailure)
    }
}

@Composable
private fun Uninitialized(
    renderOnUninitialized: @Composable (() -> Unit)?
) {
    (renderOnUninitialized.orElse(defaultUninitializedComposable))?.invoke()
}

@Composable
private fun <T> Loading(
    renderOnLoading: @Composable ((T?) -> Unit)?,
    composerAsync: ComposerAsync.Loading<T>
) {
    if (renderOnLoading != null) {
        renderOnLoading.invoke(composerAsync())
    } else {
        defaultLoadingComposable.invoke()
    }
}

@Composable
private fun <T> Success(
    renderOnSuccess: @Composable ((T?) -> Unit)?,
    composerAsync: ComposerAsync.Success<T>
) {
    if (renderOnSuccess != null) {
        renderOnSuccess.invoke(composerAsync())
    } else {
        defaultSuccessComposable.invoke()
    }
}

@Composable
private fun <T> Fail(
    renderOnFail: @Composable ((data: T?, error: Throwable) -> Unit)?,
    composerAsync: ComposerAsync.Fail<T>,
    hideOnFailure: Boolean
) {
    if (!Composer.AsyncRenderConfig.hideEveryAsyncOnFailure && !hideOnFailure) {
        if (renderOnFail != null) {
            renderOnFail.invoke(composerAsync(), composerAsync.error)
        } else {
            defaultFailComposable.invoke(composerAsync.error)
        }
    }
}
