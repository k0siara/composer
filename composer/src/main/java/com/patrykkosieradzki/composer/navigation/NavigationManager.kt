package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.hadilq.liveevent.LiveEvent
import com.patrykkosieradzki.composer.extensions.fireEvent

interface NavigationManager {
    val navigationEvent: LiveEvent<NavigationCommand>

    fun navigateTo(navDirections: NavDirections)
    fun navigateTo(@IdRes resId: Int)
    fun navigateBack()
    fun navigateBackTo(@IdRes destinationId: Int)
    fun navigateBackWithResult(requestKey: String, bundle: Bundle)
}

class NavigationManagerImpl : NavigationManager {
    override val navigationEvent: LiveEvent<NavigationCommand> = LiveEvent()

    override fun navigateTo(navDirections: NavDirections) {
        navigationEvent.fireEvent(NavigationCommand.To(navDirections))
    }

    override fun navigateTo(@IdRes resId: Int) {
        navigationEvent.fireEvent(NavigationCommand.ToId(resId))
    }

    override fun navigateBack() {
        navigationEvent.fireEvent(NavigationCommand.Back)
    }

    override fun navigateBackTo(@IdRes destinationId: Int) {
        navigationEvent.fireEvent(NavigationCommand.BackTo(destinationId))
    }

    override fun navigateBackWithResult(requestKey: String, bundle: Bundle) {
        navigationEvent.fireEvent(NavigationCommand.BackWithResult(requestKey, bundle))
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