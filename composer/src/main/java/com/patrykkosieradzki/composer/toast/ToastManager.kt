package com.patrykkosieradzki.composer.toast

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.utils.TextModel
import com.patrykkosieradzki.composer.utils.launchInLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

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

interface ToastManager {
    val toastFlow: Flow<ShowToastEffect>
    fun showToast(
        textModel: TextModel,
        duration: ShowToastEffect.Duration
    )
}

fun ToastManager.observeToastEffects(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onToastEffect: (ShowToastEffect) -> Unit = { showToastEffect ->
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
