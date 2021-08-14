package com.patrykkosieradzki.composer.toast

import android.widget.Toast
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

data class ShowToastEffect(
    val text: String,
    val duration: Int
)
interface ToastManager {
    suspend fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT)
}

class ComposerToastManager : ToastManager {
    private val _toastChannel = Channel<ShowToastEffect>(Channel.BUFFERED)
    val toastFlow = _toastChannel.consumeAsFlow()

    override suspend fun showToast(text: String, duration: Int) {
        _toastChannel.send(ShowToastEffect(text, duration))
    }
}
