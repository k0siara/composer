package com.patrykkosieradzki.composerexample.ui.home

import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.*
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.composerexample.model.Coin
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import com.patrykkosieradzki.composerexample.ui.home.HomeViewModel.HomeStateData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel(),
    UiStateManager<HomeStateData> by UiStateManagerImpl(UiState.Loading(HomeStateData())) {

    fun initialize() {
        loadCoins()
    }

    private fun loadCoins() {
        launchWithExceptionHandler {
            val coins = coinRepository.getCoins()
            updateUiStateToSuccess { it.copy(coins = coins) }
        }
    }

    fun onCoinClicked(coin: Coin) {

    }

    data class HomeStateData(
        val coins: List<Coin> = emptyList()
    )
}