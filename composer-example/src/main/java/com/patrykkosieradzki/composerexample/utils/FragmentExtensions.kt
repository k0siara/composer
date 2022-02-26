package com.patrykkosieradzki.composerexample.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.compose.LocalImageLoader
import com.patrykkosieradzki.composerexample.MainActivity
import com.patrykkosieradzki.composerexample.ui.theme.ComposerExampleTheme

fun Fragment.composeView(
    imageLoader: ImageLoader?,
    content: @Composable () -> Unit
): ComposeView {
    return ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent {
            ComposerExampleTheme {
                CompositionLocalProvider(
                    LocalImageLoader provides (imageLoader ?: LocalImageLoader.current)
                ) {
                    content()
                }
            }
        }
    }
}

fun Fragment.getImageLoaderFromMainActivity(): ImageLoader? {
    return (activity as? MainActivity)?.imageLoader
}
