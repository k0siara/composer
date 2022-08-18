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
package com.patrykkosieradzki.composer.core.event.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.event.ComposerEvent
import com.patrykkosieradzki.composer.core.event.observe

@Composable
fun <T> ComposerEventHandler(
    event: ComposerEvent<T>,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    handleEvent: (T) -> Unit
) {
    val currentHandleEvent by rememberUpdatedState(newValue = handleEvent)

    LaunchedEffect(lifecycleOwner) {
        event.observe(
            lifecycleOwner = lifecycleOwner,
            handleEvent = currentHandleEvent
        )
    }
}
