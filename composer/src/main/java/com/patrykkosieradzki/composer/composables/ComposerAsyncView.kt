package com.patrykkosieradzki.composer.composables

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.patrykkosieradzki.composer.core.Async

@Composable
fun <T> ComposerAsyncView(
    modifier: Modifier = Modifier,
    asyncProvider: () -> Async<T>,
    renderOnUninitialized: (@Composable () -> Unit)? = null,
    renderOnLoading: (@Composable (T?) -> Unit)? = null,
    renderOnEmpty: (@Composable (T?) -> Unit)? = null,
    renderOnFailure: (@Composable (Throwable, T?) -> Unit)? = null,
    renderOnSuccess: (@Composable (T?) -> Unit)? = null
) {
    val async = asyncProvider()

    Crossfade(
        modifier = Modifier.then(modifier),
        targetState = async.javaClass.kotlin,
        animationSpec = tween(300)
    ) { animatedAsync ->
        when (animatedAsync) {
            Async.Uninitialized::class -> {
                renderOnUninitialized?.invoke()
            }
            Async.Loading::class -> {
                renderOnLoading?.invoke(async.invoke())
            }
            Async.Success::class -> {
                renderOnSuccess?.invoke(async.invoke())
            }
            Async.Empty::class -> {
                renderOnEmpty?.invoke(async.invoke())
            }
            Async.Fail::class -> {
                if (async is Async.Fail) {
                    renderOnFailure?.invoke(async.error, async())
                }
            }
        }
    }
}
