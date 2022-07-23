/*
 * Copyright (C) 2022 Patryk Kosieradzki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patrykkosieradzki.composer.core.event

import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.launchInLifecycle
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
    }.launchInLifecycle(lifecycleOwner)
}
