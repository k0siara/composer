package com.patrykkosieradzki.composerexample.ui.home

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.patrykkosieradzki.composerexample.ui.theme.ComposerExampleTheme

fun composeView(
    context: Context,
    content: @Composable () -> Unit
): ComposeView {
    return ComposeView(context).apply {
        setContent {
            ComposerExampleTheme {
                content()
            }
        }
    }
}