package com.patrykkosieradzki.composer.core

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> ComposerLifecycleAwareFlowCollector(
    flow: Flow<T?>,
    lifecycleOwner: LifecycleOwner,
    save: Boolean = false,
    onEffectCallback: (T?) -> Unit
) {
    val currentOnEffectCallback by rememberUpdatedState(newValue = onEffectCallback)
    var tValue by remember { mutableStateOf<T?>(null) }
    var savedTValue by rememberSaveable { mutableStateOf<T?>(null) }

    DisposableEffect(lifecycleOwner, currentOnEffectCallback) {
        val observer = flow.onEach {
            if (save) {
                savedTValue = it
            } else {
                tValue = it
            }
        }.observeInLifecycle(lifecycleOwner).observer

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(savedTValue, tValue) {
        onEffectCallback.invoke(if (save) savedTValue else tValue)
    }
}
