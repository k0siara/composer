package com.patrykkosieradzki.composer.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface Navigator {
    val navCommands: Flow<ComposerNavCommand>

    suspend fun navigate(navCommand: ComposerNavCommand)
    suspend fun navigateWithAction(action: ComposerNavAction)
    suspend fun navigateBack()
}

class ComposerNavigator : Navigator {
    private val _navCommandsChannel = Channel<ComposerNavCommand>(Channel.BUFFERED)
    override val navCommands: Flow<ComposerNavCommand> = _navCommandsChannel.receiveAsFlow()

    override suspend fun navigate(navCommand: ComposerNavCommand) {
        _navCommandsChannel.send(navCommand)
    }

    override suspend fun navigateWithAction(action: ComposerNavAction) {
        navigate(ComposerNavCommand.WithAction(action))
    }

    override suspend fun navigateBack() {
        navigate(ComposerNavCommand.Back)
    }
}
