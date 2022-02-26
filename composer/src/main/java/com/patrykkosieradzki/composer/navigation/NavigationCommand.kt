package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections

interface NavigationCommand

sealed interface ComposerNavigationCommand : NavigationCommand {
    data class To(val directions: NavDirections) : ComposerNavigationCommand
    data class ToId(val resId: Int) : ComposerNavigationCommand
    object Back : ComposerNavigationCommand
    data class BackTo(@IdRes val destinationId: Int, val inclusive: Boolean = false) :
        ComposerNavigationCommand

    data class BackWithResult(val requestKey: String, val bundle: Bundle) : ComposerNavigationCommand
    data class Custom(val navCall: (NavController) -> Unit) : ComposerNavigationCommand
}
