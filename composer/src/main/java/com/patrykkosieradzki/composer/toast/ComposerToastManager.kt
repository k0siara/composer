package com.patrykkosieradzki.composer.toast

import android.widget.Toast
import androidx.activity.ComponentActivity
import com.patrykkosieradzki.composer.utils.observeOnLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow

data class ShowToastEffect(
    val text: String,
    val duration: Int
)

interface ToastManager {
    suspend fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT)

    fun observeToastEffects(
        activity: ComponentActivity,
        customOnToastEffect: ((ShowToastEffect) -> Unit)? = null
    )
}

class ComposerToastManager : ToastManager {
    private val _toastChannel = Channel<ShowToastEffect>(Channel.BUFFERED)
    val toastFlow = _toastChannel.consumeAsFlow()

    override suspend fun showToast(text: String, duration: Int) {
        _toastChannel.send(ShowToastEffect(text, duration))
    }

    override fun observeToastEffects(
        activity: ComponentActivity,
        customOnToastEffect: ((ShowToastEffect) -> Unit)?
    ) {
        toastFlow.observeOnLifecycle(activity) {
            if (customOnToastEffect != null) {
                customOnToastEffect.invoke(it)
            } else {
                Toast.makeText(activity, it.text, it.duration).show()
            }
        }
    }
}
