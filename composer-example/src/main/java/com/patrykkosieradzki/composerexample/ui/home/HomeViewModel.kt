package com.patrykkosieradzki.composerexample.ui.home

import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.ComposerUIState
import com.patrykkosieradzki.composer.ComposerUIStateData
import com.patrykkosieradzki.composer.ComposerViewModel
import com.patrykkosieradzki.composerexample.model.Coin
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ComposerViewModel<HomeState>(
    initialState = ComposerUIState.Loading
) {
    init {
        loadCoins()
    }

    private fun loadCoins() {
        viewModelScope.launch {
            val coins = coinRepository.getCoins()
            updateUiState { ComposerUIState.Success(HomeState(coins = coins)) }
        }
    }
}

data class HomeState(
    val coins: List<Coin>
) : ComposerUIStateData