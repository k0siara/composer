package com.patrykkosieradzki.composer.toast

import android.widget.Toast
import androidx.activity.ComponentActivity
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
    componentActivity: ComponentActivity,
    customOnToastEffect: ((ShowToastEffect) -> Unit)? = null
) {
    toastFlow.onEach {
        if (customOnToastEffect != null) {
            customOnToastEffect.invoke(it)
        } else {
            Toast.makeText(componentActivity, it.text, it.duration).show()
        }
    }.observeInLifecycle(componentActivity)
}