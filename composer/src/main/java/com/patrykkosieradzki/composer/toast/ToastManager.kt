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

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.toast.impl.ToastManagerImpl
import com.patrykkosieradzki.composer.utils.TextModel
import com.patrykkosieradzki.composer.utils.launchInLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface ToastManager {
    data class ShowToastEffect(
        val textModel: TextModel,
        val duration: Duration
    ) {
        sealed class Duration(
            val toastLengthInt: Int
        ) {
            object Short : Duration(toastLengthInt = Toast.LENGTH_SHORT)
            object Long : Duration(toastLengthInt = Toast.LENGTH_LONG)
        }
    }

    val toastFlow: Flow<ShowToastEffect>
    fun showToast(showToastEffect: ShowToastEffect)

    companion object {
        fun delegate(): ToastManager = ToastManagerImpl()
    }
}

fun ToastManager.observeToastEffects(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onToastEffect: (ToastManager.ShowToastEffect) -> Unit = { showToastEffect ->
        val text = showToastEffect.textModel.resolveText(context.resources)
        Toast.makeText(
            context,
            text,
            showToastEffect.duration.toastLengthInt
        ).show()
    }
) {
    toastFlow
        .onEach { showToastEffect -> onToastEffect(showToastEffect) }
        .launchInLifecycle(lifecycleOwner)
}
