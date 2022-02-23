package com.patrykkosieradzki.composerexample.ui.home

import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.*
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

    init {
        loadCoins()
    }

    private fun loadCoins() {
//        safeLaunch {
//            val coins = coinRepository.getCoins()
//            updateUiState { UiState.Success(HomeStateData(coins = coins)) }
//        }
    }

    data class HomeStateData(
        val coins: List<Coin> = emptyList()
    )
}