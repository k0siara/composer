package com.patrykkosieradzki.composer.core

interface ComposerEventHandler<E : ComposerUiEvent> {
    fun handleEvent(event: E)
}
