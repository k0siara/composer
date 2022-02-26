package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavDirections

sealed interface NavigationCommand {
    data class To(val directions: NavDirections) : NavigationCommand
    data class ToId(val resId: Int) : NavigationCommand
    object Back : NavigationCommand
    data class BackTo(@IdRes val destinationId: Int, val inclusive: Boolean = false) :
        NavigationCommand

    data class BackWithResult(val requestKey: String, val bundle: Bundle) : NavigationCommand
}
