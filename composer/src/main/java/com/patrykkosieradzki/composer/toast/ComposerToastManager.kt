package com.patrykkosieradzki.composer.toast

import android.widget.Toast
import androidx.activity.ComponentActivity
import com.patrykkosieradzki.composer.core.ComposerEffectHandler
import com.patrykkosieradzki.composer.extensions.fireEffect

data class ShowToastEffect(
    val text: String,
    val duration: Int
)

interface ToastManager {
    val toastEffect: ComposerEffectHandler<ShowToastEffect>
    fun showToast(text: String, duration: Int = Toast.LENGTH_SHORT)
}

class ComposerToastManager : ToastManager {
    override val toastEffect: ComposerEffectHandler<ShowToastEffect> = ComposerEffectHandler()

    override fun showToast(text: String, duration: Int) {
        toastEffect.fireEffect(ShowToastEffect(text, duration))
    }
}

fun ToastManager.observeToastEffects(
    componentActivity: ComponentActivity,
    customOnToastEffect: ((ShowToastEffect) -> Unit)? = null
) {
    toastEffect.observe(componentActivity) {
        if (customOnToastEffect != null) {
            customOnToastEffect.invoke(it)
        } else {
            Toast.makeText(componentActivity, it.text, it.duration).show()
        }
    }
}