package com.patrykkosieradzki.composer.toast

import android.widget.Toast
import androidx.activity.ComponentActivity
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach

data class ShowToastEffect(
    val text: String,
    val duration: Int
)

interface ToastManager {
    val toastFlow: Flow<ShowToastEffect>
    suspend fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT)
}

class ComposerToastManager : ToastManager {
    private val _toastChannel = Channel<ShowToastEffect>(Channel.BUFFERED)
    override val toastFlow = _toastChannel.consumeAsFlow()

    override suspend fun showToast(text: String, duration: Int) {
        _toastChannel.send(ShowToastEffect(text, duration))
    }
}

fun ComponentActivity.observeToastEffects(
    toastManager: ToastManager,
    customOnToastEffect: ((ShowToastEffect) -> Unit)? = null
) {
    toastManager.toastFlow.onEach {
        if (customOnToastEffect != null) {
            customOnToastEffect.invoke(it)
        } else {
            Toast.makeText(this, it.text, it.duration).show()
        }
    }.observeInLifecycle(this)
}