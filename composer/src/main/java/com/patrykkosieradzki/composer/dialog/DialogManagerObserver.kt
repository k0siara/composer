package com.patrykkosieradzki.composer.dialog

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector

@Composable
fun DialogManagerObserver(
    dialogManager: DialogManager,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    dialogContent: @Composable (ComposerDialog) -> Unit
) {
    var dialog by remember { mutableStateOf<ComposerDialog?>(null) }

    ComposerLifecycleAwareFlowCollector(
        flow = dialogManager.dialogFlow,
        lifecycleOwner = lifecycleOwner,
        save = true,
        onEffectCallback = { dialog = it }
    )

    dialog?.let { dialogContent.invoke(it) }
}
