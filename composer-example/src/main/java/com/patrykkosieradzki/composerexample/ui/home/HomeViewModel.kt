package com.patrykkosieradzki.composerexample.ui.home

import com.patrykkosieradzki.composer.ComposerUIState
import com.patrykkosieradzki.composer.ComposerUIStateData
import com.patrykkosieradzki.composer.ComposerViewModel
import com.patrykkosieradzki.composerexample.model.Coin
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ComposerViewModel<HomeState>(
    initialState = ComposerUIState.Loading
) {

}

data class HomeState(
    val coins: List<Coin>
) : ComposerUIStateData