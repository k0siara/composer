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
package com.patrykkosieradzki.composer.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import com.patrykkosieradzki.composer.core.event.StateFlowEvent
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <T> ComposerFlowEventHandler(
    event: StateFlowEvent<T>,
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
