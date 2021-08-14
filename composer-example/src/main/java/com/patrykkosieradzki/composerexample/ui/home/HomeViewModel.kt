package com.patrykkosieradzki.composerexample.ui.home

import com.patrykkosieradzki.composer.core.ComposerUiState
import com.patrykkosieradzki.composer.core.ComposerUiStateData
import com.patrykkosieradzki.composer.core.ComposerViewModel
import com.patrykkosieradzki.composerexample.model.Coin
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ComposerViewModel<HomeStateData>(
    initialState = ComposerUiState.Loading(HomeStateData())
) {
    init {
        loadCoins()
    }

    private fun loadCoins() {
        safeLaunch {
            val coins = coinRepository.getCoins()
            updateUiState { ComposerUiState.Success(HomeStateData(coins = coins)) }
        }
    }
}

data class HomeStateData(
    val coins: List<Coin> = emptyList()
) : ComposerUiStateData