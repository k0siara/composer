package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.patrykkosieradzki.composer.core.ComposerEffectHandler
import com.patrykkosieradzki.composer.extensions.fireEffect

interface NavigationManager {
    val navigationEvent: ComposerEffectHandler<NavigationCommand>

    fun navigateTo(navDirections: NavDirections)
    fun navigateTo(@IdRes resId: Int)
    fun navigateBack()
    fun navigateBackTo(@IdRes destinationId: Int)
    fun navigateBackWithResult(requestKey: String, bundle: Bundle)
}

class NavigationManagerImpl : NavigationManager {
    override val navigationEvent: ComposerEffectHandler<NavigationCommand> = ComposerEffectHandler()

    override fun navigateTo(navDirections: NavDirections) {
        navigationEvent.fireEffect(NavigationCommand.To(navDirections))
    }

    override fun navigateTo(@IdRes resId: Int) {
        navigationEvent.fireEffect(NavigationCommand.ToId(resId))
    }

    override fun navigateBack() {
        navigationEvent.fireEffect(NavigationCommand.Back)
    }

    override fun navigateBackTo(@IdRes destinationId: Int) {
        navigationEvent.fireEffect(NavigationCommand.BackTo(destinationId))
    }

    override fun navigateBackWithResult(requestKey: String, bundle: Bundle) {
        navigationEvent.fireEffect(NavigationCommand.BackWithResult(requestKey, bundle))
    }
}

fun NavigationManager.observeNavigation(
    fragment: Fragment,
    onOtherNavigationCommand: ((NavigationCommand) -> Unit)? = null
) {
    val navController = fragment.findNavController()

    navigationEvent.observe(fragment.viewLifecycleOwner) {
        when (it) {
            is NavigationCommand.To -> {
                navController.navigate(it.directions)
            }
            is NavigationCommand.ToId -> {
                navController.navigate(it.resId)
            }
            is NavigationCommand.Back -> {
                navController.navigateUp()
            }
            is NavigationCommand.BackTo -> {
                if (navController.popBackStack(
                        destinationId = it.destinationId,
                        inclusive = it.inclusive
                    ).not()
                ) {
                    navController.navigate(it.destinationId)
                }
            }
            is NavigationCommand.BackWithResult -> {
                fragment.setFragmentResult(
                    requestKey = it.requestKey,
                    result = it.bundle
                )
                navController.popBackStack()
            }
            else -> {
                onOtherNavigationCommand?.invoke(it)
                    ?: throw IllegalStateException("Unknown navigation command")
            }
        }
    }
}

fun Fragment.registerBackNavigationHandler(
    onBackPressed: (() -> Unit)? = null
) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
        onBackPressed?.invoke() ?: try {
            findNavController().navigateUp()
        } catch (e: IllegalStateException) {
            Log.e(
                "NavigationManager",
                "Fragment $this is not a NavHostFragment or within a NavHostFragment",
                e
            )
        }
    }
}