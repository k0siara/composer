package com.patrykkosieradzki.composer.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun ViewModel.launchWithExceptionHandler(
    onFailure: ((Throwable) -> Unit)? = null,
    block: suspend CoroutineScope.() -> Unit
) {
    viewModelScope.launch(
        context = CoroutineExceptionHandler { _, throwable -> onFailure?.invoke(throwable) },
        block = block
    )
}