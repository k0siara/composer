package com.patrykkosieradzki.composer.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@InternalCoroutinesApi
@Composable
fun <T> ComposerLifecycleAwareFlowCollector(
    flow: Flow<T>,
    lifecycleOwner: LifecycleOwner,
    onEffectCallback: (T) -> Unit
) {
    DisposableEffect(Unit) {
        val observer = flow.onEach {
            onEffectCallback(it)
        }.observeInLifecycle(lifecycleOwner).observer

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}