package com.patrykkosieradzki.composer.toast

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.onEach

@InternalCoroutinesApi
@Composable
fun ComposerToastHandler(
    composerToastManager: ComposerToastManager,
    context: Context = LocalContext.current,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    LaunchedEffect(lifecycleOwner, context, composerToastManager) {
        val observer = Observer<ShowToastEffect> {
            Toast.makeText(context, it.text, it.duration).show()
        }
        composerToastManager.toastEffect.observe(lifecycleOwner, observer)
    }
}
