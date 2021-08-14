package com.patrykkosieradzki.composer.navigation

sealed class ComposerNavCommand {
    data class WithAction(val action: ComposerNavAction) : ComposerNavCommand()
    object Back : ComposerNavCommand()
}
