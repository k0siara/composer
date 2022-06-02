package com.patrykkosieradzki.composer.core

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> ComposerLifecycleAwareFlowCollector(
    flow: Flow<T?>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onEach: (T?) -> Unit
) {
    val currentOnEach by rememberUpdatedState(newValue = onEach)

    DisposableEffect(lifecycleOwner, currentOnEach) {
        val observer = flow.onEach { currentOnEach(it) }.observeInLifecycle(lifecycleOwner).observer

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
