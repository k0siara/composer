package com.patrykkosieradzki.composerexample.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiState
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiStateManager
import com.patrykkosieradzki.composer.core.state.simple.SimpleUiStateManagerImpl
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.composer.navigation.NavigationManager
import com.patrykkosieradzki.composer.navigation.NavigationManagerImpl
import com.patrykkosieradzki.composerexample.model.CoinResponse
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CoinDetailsViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) : ViewModel(),
    SimpleUiStateManager by SimpleUiStateManagerImpl(SimpleUiState.Loading),
    NavigationManager by NavigationManagerImpl() {

    val coin: MutableStateFlow<CoinResponse?> = MutableStateFlow(null)

    fun initialize(coinId: String) {
        launchWithExceptionHandler(
            onFailure = {
                Log.e("CoinDetailsViewModel", "Coin details fetch error", it)
                updateUiStateToFailure(it)
            }
        ) {
            val coinDetails = coinRepository.getCoinDetails(coinId)
            updateUiStateToSuccess {
                coin.update { coinDetails }
            }
        }
    }

    fun onBackArrowClicked() {
        navigateBack()
    }
}