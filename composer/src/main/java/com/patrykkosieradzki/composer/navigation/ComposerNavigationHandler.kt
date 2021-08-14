package com.patrykkosieradzki.composer.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@Composable
fun ComposerNavigationHandler(
    composerNavigator: ComposerNavigator,
    navHostController: NavHostController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
    ComposerLifecycleAwareFlowCollector(
        flow = composerNavigator.navCommands,
        lifecycleOwner = lifecycleOwner
    ) {
        when (it) {
            is ComposerNavCommand.WithAction -> {
                val action = it.action
                action.parcelables.forEach { arg ->
                    navHostController.currentBackStackEntry?.arguments?.putParcelable(
                        arg.key,
                        arg.value
                    )
                }
                navHostController.navigate(action.destination, action.navOptions)
            }
            is ComposerNavCommand.Back -> {
                navHostController.popBackStack()
            }
        }
    }
}