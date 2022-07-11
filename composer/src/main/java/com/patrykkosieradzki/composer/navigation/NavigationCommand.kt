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
