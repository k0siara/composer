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
import androidx.activity.ComponentActivity
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

data class ShowToastEffect(
    val text: String,
    val duration: Int
)

interface ToastManager {
    val toastFlow: Flow<ShowToastEffect>
    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT)
}

class ComposerToastManager : ToastManager {
    private val toastChannel: Channel<ShowToastEffect> = Channel(UNLIMITED)
    override val toastFlow: Flow<ShowToastEffect>
        get() = toastChannel.receiveAsFlow()

    override fun showToast(text: String, duration: Int) {
        toastChannel.trySend(ShowToastEffect(text, duration))
    }
}

fun ToastManager.observeToastEffects(
    activity: ComponentActivity,
    customOnToastEffect: ((ShowToastEffect) -> Unit)? = null
) = observeToastEffects(
    context = activity,
    lifecycleOwner = activity,
    customOnToastEffect = customOnToastEffect
)

fun ToastManager.observeToastEffects(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    customOnToastEffect: ((ShowToastEffect) -> Unit)? = null
) {
    toastFlow.onEach {
        if (customOnToastEffect != null) {
            customOnToastEffect.invoke(it)
        } else {
            Toast.makeText(context, it.text, it.duration).show()
        }
    }.observeInLifecycle(lifecycleOwner)
}
