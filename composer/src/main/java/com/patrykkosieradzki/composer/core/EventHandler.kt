package com.patrykkosieradzki.composer.core

interface EventHandler<E : UiEvent> {
    fun handleEvent(event: E)
}
