package com.patrykkosieradzki.composer

abstract class ComposerWithEventHandlerViewModel<S : ComposerUIStateData, E : ComposerUIEvent>(
    private val initialState: ComposerUIState<S> = ComposerUIState.Loading,
) : ComposerViewModel<S>(initialState) {
    val eventHandler: (E) -> Unit = ::handleEvent
    protected abstract fun handleEvent(event: E)
}