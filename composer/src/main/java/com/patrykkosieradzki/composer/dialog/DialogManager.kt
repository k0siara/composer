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
package com.patrykkosieradzki.composer.dialog

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface ComposerDialog

interface DialogManager {
    val dialogFlow: Flow<ComposerDialog?>

    fun showDialog(composerDialog: ComposerDialog)
    fun closeDialog()
}

class DialogManagerImpl : DialogManager {
    private val channel: Channel<ComposerDialog?> = Channel(UNLIMITED)
    override val dialogFlow: Flow<ComposerDialog?>
        get() = channel.receiveAsFlow()

    override fun showDialog(composerDialog: ComposerDialog) {
        channel.trySend(composerDialog)
    }

    override fun closeDialog() {
        channel.trySend(null)
    }
}
