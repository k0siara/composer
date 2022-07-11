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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector

@Composable
fun ComposerDialogComposable(
    dialogManager: DialogManager,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    dialogContent: @Composable (ComposerDialog) -> Unit
) {
    var dialog by remember { mutableStateOf<ComposerDialog?>(null) }

    ComposerLifecycleAwareFlowCollector(
        flow = dialogManager.dialogFlow,
        lifecycleOwner = lifecycleOwner,
        onEach = { dialog = it }
    )

    dialog?.let { dialogContent.invoke(it) }
}
