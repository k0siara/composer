package com.patrykkosieradzki.composer.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ComposerToastHandler(
    composerToastManager: ComposerToastManager,
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    ComposerLifecycleAwareFlowCollector(
        flow = composerToastManager.toastFlow,
        lifecycleOwner = lifecycleOwner
    ) { showToastEffect ->
        showToastEffect?.let {
            Toast.makeText(context, it.text, it.duration).show()
        }
    }
}
