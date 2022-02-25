package com.patrykkosieradzki.composer.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.StateFlow

@Composable
fun <T> StateFlow<T>.asLifecycleAwareState(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) = lifecycleAwareState(lifecycleOwner, this,)

@Composable
fun <T> lifecycleAwareState(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    stateFlow: StateFlow<T>
): State<T> {
    val initialValue = remember(stateFlow) { stateFlow.value }
    val lifecycleAwareStateFlow = remember(stateFlow, lifecycleOwner) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
    }
    return lifecycleAwareStateFlow.collectAsState(initialValue)
}