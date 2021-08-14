package com.patrykkosieradzki.composerexample.navigation

import com.patrykkosieradzki.composer.navigation.ComposerNavAction

object MainActions {
    val Default = ComposerNavAction(
        destination = ""
    )
    val root = ComposerNavAction(
        destination = "home-root"
    )
    val home = ComposerNavAction(
        destination = "home"
    )
}