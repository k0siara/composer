package com.patrykkosieradzki.composer.dialog

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow

interface ComposerDialog

interface DialogManager {
    val dialogFlow: Flow<ComposerDialog?>

    fun showDialog(composerDialog: ComposerDialog)
    fun closeDialog()
}

class DialogManagerImpl : DialogManager {
    private val channel: Channel<ComposerDialog?> = Channel(UNLIMITED)
    override val dialogFlow: Flow<ComposerDialog?>
        get() = channel.consumeAsFlow()

    override fun showDialog(composerDialog: ComposerDialog) {
        channel.trySend(composerDialog)
    }

    override fun closeDialog() {
        channel.trySend(null)
    }
}
