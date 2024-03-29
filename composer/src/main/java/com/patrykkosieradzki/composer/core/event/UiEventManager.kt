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
import com.patrykkosieradzki.composer.core.event.impl.UiEventManagerImpl
import com.patrykkosieradzki.composer.utils.launchInLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface UiEvent

interface UiEventManager<E : UiEvent> {
    val uiEventFlow: Flow<E>
    fun emitUiEvent(event: E)

    companion object {
        fun <E : UiEvent> delegate(): UiEventManager<E> {
            return UiEventManagerImpl()
        }
    }
}

fun <E : UiEvent> UiEventManager<E>.observeUiEvents(
    lifecycleOwner: LifecycleOwner,
    onEvent: (E) -> Unit
) {
    uiEventFlow
        .onEach { onEvent(it) }
        .launchInLifecycle(lifecycleOwner)
}
