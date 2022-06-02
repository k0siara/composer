package com.patrykkosieradzki.composer.composables

import androidx.compose.runtime.*
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

@Composable
fun <T> ComposerFlowEventHandler(
    event: ComposerFlowEvent<T>,
    handleEvent: (T, CoroutineScope) -> Unit
) {
    val eventFiredState by event.firedFlow().asLifecycleAwareState()
    val eventState by event.flow().asLifecycleAwareState()

    val currentHandleEvent by rememberUpdatedState(newValue = handleEvent)

    LaunchedEffect(Unit) {
        combine(
            snapshotFlow { eventFiredState },
            snapshotFlow { eventState },
            transform = { fired, state -> if (fired) state else null }
        ).filterNotNull()
            .onEach { state ->
                currentHandleEvent.invoke(state, this@LaunchedEffect)
                event.onEventHandled()
            }.launchIn(scope = this@LaunchedEffect)
    }
}