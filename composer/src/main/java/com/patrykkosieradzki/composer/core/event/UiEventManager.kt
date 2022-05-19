package com.patrykkosieradzki.composer.core.event

import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

interface UiEventManager<E : UiEvent> {
    val uiEventFlow: Flow<E>
    fun emitUiEvent(event: E)
}

class UiEventManagerImpl<T : UiEvent> : UiEventManager<T> {
    private val _eventsChannel: Channel<T> = Channel(Channel.UNLIMITED)
    override val uiEventFlow: Flow<T> = _eventsChannel.receiveAsFlow()

    override fun emitUiEvent(event: T) {
        _eventsChannel.trySend(event)
    }
}

fun <T : UiEvent> uiEventManagerDelegate() = UiEventManagerImpl<T>()

fun <E : UiEvent> UiEventManager<E>.observeUiEvents(
    lifecycleOwner: LifecycleOwner,
    onEvent: (E) -> Unit
) {
    uiEventFlow.onEach {
        onEvent(it)
    }.observeInLifecycle(lifecycleOwner)
}