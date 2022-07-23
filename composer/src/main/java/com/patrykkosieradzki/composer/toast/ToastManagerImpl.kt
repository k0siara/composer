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
package com.patrykkosieradzki.composer.toast

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

internal class ToastManagerImpl : ToastManager {
    private val toastChannel: Channel<ToastManager.ShowToastEffect> = Channel(UNLIMITED)
    override val toastFlow: Flow<ToastManager.ShowToastEffect>
        get() = toastChannel.receiveAsFlow()

    override fun showToast(showToastEffect: ToastManager.ShowToastEffect) {
        toastChannel.trySend(showToastEffect)
    }
}