package com.patrykkosieradzki.composer.composables

import androidx.compose.runtime.Composable
import com.patrykkosieradzki.composer.core.Async
import com.patrykkosieradzki.composer.core.Composer
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultFailComposable
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultLoadingComposable
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultSuccessComposable
import com.patrykkosieradzki.composer.core.Composer.AsyncRenderConfig.Companion.defaultUninitializedComposable
import com.patrykkosieradzki.composer.utils.orElse

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
        is Async.Uninitialized -> Uninitialized(renderOnUninitialized)
        is Async.Loading -> Loading(renderOnLoading, async)
        is Async.Success -> Success(renderOnSuccess, async)
        is Async.Fail -> Fail(renderOnFail, async, hideOnFailure)
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
    async: Async.Loading<T>
) {
    if (renderOnLoading != null) {
        renderOnLoading.invoke(async())
    } else {
        defaultLoadingComposable.invoke()
    }
}

@Composable
private fun <T> Success(
    renderOnSuccess: @Composable ((T?) -> Unit)?,
    async: Async.Success<T>
) {
    if (renderOnSuccess != null) {
        renderOnSuccess.invoke(async())
    } else {
        defaultSuccessComposable.invoke()
    }
}

@Composable
private fun <T> Fail(
    renderOnFail: @Composable ((data: T?, error: Throwable) -> Unit)?,
    async: Async.Fail<T>,
    hideOnFailure: Boolean
) {
    if (!Composer.AsyncRenderConfig.hideEveryAsyncOnFailure && !hideOnFailure) {
        if (renderOnFail != null) {
            renderOnFail.invoke(async(), async.error)
        } else {
            defaultFailComposable.invoke(async.error)
        }
    }
}
