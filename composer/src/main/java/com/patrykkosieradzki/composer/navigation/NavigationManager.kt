package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

interface NavigationManager {
    val navigationCommandFlow: Flow<NavigationCommand>

    fun navigate(navigationCommand: NavigationCommand)
    fun navigateTo(navDirections: NavDirections)
    fun navigateTo(@IdRes resId: Int)
    fun navigateBack()
    fun navigateBackTo(@IdRes destinationId: Int)
    fun navigateBackWithResult(requestKey: String, bundle: Bundle)
}

class NavigationManagerImpl : NavigationManager {
    private val navCommandsChannel: Channel<NavigationCommand> = Channel(UNLIMITED)
    override val navigationCommandFlow: Flow<NavigationCommand> = navCommandsChannel.receiveAsFlow()

    override fun navigateTo(navDirections: NavDirections) {
        navigate(NavigationCommand.To(navDirections))
    }

    override fun navigateTo(@IdRes resId: Int) {
        navigate(NavigationCommand.ToId(resId))
    }

    override fun navigateBack() {
        navigate(NavigationCommand.Back)
    }

    override fun navigateBackTo(@IdRes destinationId: Int) {
        navigate(NavigationCommand.BackTo(destinationId))
    }

    override fun navigateBackWithResult(requestKey: String, bundle: Bundle) {
        navigate(NavigationCommand.BackWithResult(requestKey, bundle))
    }

    override fun navigate(navigationCommand: NavigationCommand) {
        navCommandsChannel.trySend(navigationCommand)
    }
}

fun NavigationManager.observeNavigation(
    fragment: Fragment,
    onOtherNavigationCommand: ((NavigationCommand) -> Unit)? = null
) {
    val navController = fragment.findNavController()

    navigationCommandFlow.onEach {
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
    }.observeInLifecycle(fragment.viewLifecycleOwner)
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