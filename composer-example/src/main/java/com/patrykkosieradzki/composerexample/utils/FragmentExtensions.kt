package com.patrykkosieradzki.composerexample.utils

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.patrykkosieradzki.composerexample.ui.theme.ComposerExampleTheme

fun composeView(
    context: Context,
    content: @Composable () -> Unit
): ComposeView {
    return ComposeView(context).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ComposerExampleTheme {
                content()
            }
        }
    }
}