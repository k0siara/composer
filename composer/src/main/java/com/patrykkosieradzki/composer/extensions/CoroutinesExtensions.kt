package com.patrykkosieradzki.composer.extensions

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.launchWithExceptionHandler(
    onFailure: ((Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
) {
    launch(
        context = CoroutineExceptionHandler { _, throwable -> onFailure?.invoke(throwable) },
        block = block
    )
}