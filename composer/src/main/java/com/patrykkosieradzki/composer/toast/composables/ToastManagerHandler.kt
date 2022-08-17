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
package com.patrykkosieradzki.composer.toast.composables

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector
import com.patrykkosieradzki.composer.toast.ToastManager
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ToastManagerHandler(
    toastManager: ToastManager,
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onToastEffect: (ToastManager.ShowToastEffect) -> Unit = { showToastEffect ->
        val text = showToastEffect.textModel.resolveText(context.resources)
        Toast.makeText(
            context,
            text,
            showToastEffect.duration.toastLengthInt
        ).show()
    }
) {
    ComposerLifecycleAwareFlowCollector(
        flow = toastManager.toastFlow,
        lifecycleOwner = lifecycleOwner,
        onEach = { showToastEffect -> showToastEffect?.let { onToastEffect(it) } }
    )
}
