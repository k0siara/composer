package com.patrykkosieradzki.composer.core.event

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

internal class UiEventManagerImpl<T : UiEvent> : UiEventManager<T> {
    private val _eventsChannel: Channel<T> = Channel(Channel.UNLIMITED)
    override val uiEventFlow: Flow<T> = _eventsChannel.receiveAsFlow()

    override fun emitUiEvent(event: T) {
        _eventsChannel.trySend(event)
    }
}
