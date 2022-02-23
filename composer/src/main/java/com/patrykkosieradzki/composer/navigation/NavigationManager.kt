package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections
import com.hadilq.liveevent.LiveEvent
import com.patrykkosieradzki.composer.extensions.fireEvent

interface NavigationManager {
    val navigationCommandEvent: LiveEvent<NavigationCommand>
}

class NavigationManagerImpl : NavigationManager {
    override val navigationCommandEvent: LiveEvent<NavigationCommand> = LiveEvent()

    fun navigateTo(navDirections: NavDirections) {
        navigationCommandEvent.fireEvent(NavigationCommand.To(navDirections))
    }

    fun navigateTo(@IdRes resId: Int) {
        navigationCommandEvent.fireEvent(NavigationCommand.ToId(resId))
    }

    fun navigateBack() {
        navigationCommandEvent.fireEvent(NavigationCommand.Back)
    }

    fun navigateBackTo(@IdRes destinationId: Int) {
        navigationCommandEvent.fireEvent(NavigationCommand.BackTo(destinationId))
    }

    fun navigateBackWithResult(requestKey: String, bundle: Bundle) {
        navigationCommandEvent.fireEvent(NavigationCommand.BackWithResult(requestKey, bundle))
    }
}