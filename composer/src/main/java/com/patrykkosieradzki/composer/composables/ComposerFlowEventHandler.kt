package com.patrykkosieradzki.composer.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.patrykkosieradzki.composer.core.event.ComposerFlowEvent
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState
import kotlinx.coroutines.CoroutineScope

@Composable
fun <T> ComposerFlowEventHandler(
    event: ComposerFlowEvent<T>,
    handleEvent: (T, CoroutineScope) -> Unit
) {
    val eventFiredState by event.firedFlow().asLifecycleAwareState()
    val eventState by event.flow().asLifecycleAwareState()

    if (eventFiredState && eventState != null) {
        LaunchedEffect(Unit) {
            event.onEventHandled()
            eventState?.let { event -> handleEvent.invoke(event, this) }
        }
    }
}