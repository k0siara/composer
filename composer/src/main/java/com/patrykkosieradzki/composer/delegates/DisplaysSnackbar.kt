package com.patrykkosieradzki.composer.delegates

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SnackbarState(
    val isShown: Boolean = false,
    val message: String = ""
)

interface DisplaysSnackbar {
    val initialSnackbarState: SnackbarState
    val _snackbarState: MutableStateFlow<SnackbarState>
    val snackbarState: StateFlow<SnackbarState>

    fun showSnackbar(message: String)

    fun dismissSnackbar()
}

class DisplaysSnackbarDelegate(
    override val initialSnackbarState: SnackbarState = SnackbarState()
) : DisplaysSnackbar {
    override val _snackbarState: MutableStateFlow<SnackbarState> by lazy {
        MutableStateFlow(initialSnackbarState)
    }
    override val snackbarState: StateFlow<SnackbarState> = _snackbarState.asStateFlow()

    private val currentSnackbarState: SnackbarState
        get() = snackbarState.value

    override fun showSnackbar(message: String) {
        _snackbarState.value = currentSnackbarState.copy(isShown = true, message = message)
    }

    override fun dismissSnackbar() {
        _snackbarState.value = currentSnackbarState.copy(isShown = false)
    }
}
