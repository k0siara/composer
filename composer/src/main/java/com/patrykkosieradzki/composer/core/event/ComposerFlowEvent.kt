package com.patrykkosieradzki.composer.core.event

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ComposerFlowEvent<T> {
    private val _eventState by lazy { MutableStateFlow<T?>(null) }
    private val _firedState by lazy { MutableStateFlow(false) }

    fun flow() = _eventState.asStateFlow()
    fun firedFlow() = _firedState.asStateFlow()

    fun fireEvent(t: T) {
        _eventState.update { t }
        _firedState.update { true }
    }

    fun onEventHandled() {
        _eventState.update { null }
        _firedState.update { false }
    }
}
