/*
 * Copyright (C) 2022 Patryk Kosieradzki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patrykkosieradzki.composer.navigation

import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.patrykkosieradzki.composer.utils.launchInLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

interface NavigationManager {
    val navigationCommandFlow: Flow<NavigationCommand>

    fun navigate(navigationCommand: NavigationCommand)
    fun navigateTo(navDirections: NavDirections)
    fun navigateTo(@IdRes resId: Int)
    fun navigateBack()
    fun navigateBackTo(@IdRes destinationId: Int)
    fun navigateBackWithResult(requestKey: String, bundle: Bundle)

    companion object {
        fun delegate(): NavigationManager = NavigationManagerImpl()
    }
}

fun NavigationManager.observeNavigation(
    fragment: Fragment,
    onOtherNavigationCommand: ((NavigationCommand) -> Unit)? = null
) {
    val navController = fragment.findNavController()

    navigationCommandFlow.onEach {
        when (it) {
            is ComposerNavigationCommand.To -> {
                navController.navigate(it.directions)
            }
            is ComposerNavigationCommand.ToId -> {
                navController.navigate(it.resId)
            }
            is ComposerNavigationCommand.Back -> {
                navController.navigateUp()
            }
            is ComposerNavigationCommand.BackTo -> {
                if (navController.popBackStack(
                        destinationId = it.destinationId,
                        inclusive = it.inclusive
                    ).not()
                ) {
                    navController.navigate(it.destinationId)
                }
            }
            is ComposerNavigationCommand.BackWithResult -> {
                fragment.setFragmentResult(
                    requestKey = it.requestKey,
                    result = it.bundle
                )
                navController.popBackStack()
            }
            is ComposerNavigationCommand.Custom -> {
                it.navCall(navController)
            }
            else -> {
                onOtherNavigationCommand?.invoke(it)
                    ?: throw IllegalStateException("Unknown navigation command")
            }
        }
    }.launchInLifecycle(fragment.viewLifecycleOwner)
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
