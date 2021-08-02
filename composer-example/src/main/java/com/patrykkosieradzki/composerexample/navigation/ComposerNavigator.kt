package com.patrykkosieradzki.composerexample.navigation

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow


interface NavigationAction {
//    val arguments: List<NamedNavArguments>
    val destination: String
}

interface ComposerNavigator {
    val navActions: Flow<NavigationAction>
    suspend fun navigateTo(navAction: NavigationAction)
}

class ComposerNavigatorImpl : ComposerNavigator {
    private val _navActions: Channel<NavigationAction> by lazy { Channel() }
    override val navActions = _navActions.receiveAsFlow()

    override suspend fun navigateTo(navAction: NavigationAction) {
        _navActions.send(navAction)
    }
}