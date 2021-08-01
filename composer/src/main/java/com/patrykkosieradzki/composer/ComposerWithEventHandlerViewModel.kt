package com.patrykkosieradzki.composer

abstract class ComposerWithEventHandlerViewModel<S : ComposerUIStateData, E : ComposerUIEvent>(
    override val initialState: ComposerUIState<S> = ComposerUIState.Loading,
) : ComposerViewModel<S>() {
    val eventHandler: (E) -> Unit = ::handleEvent
    protected abstract fun handleEvent(event: E)
}