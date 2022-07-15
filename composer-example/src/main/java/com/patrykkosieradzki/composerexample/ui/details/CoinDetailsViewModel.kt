package com.patrykkosieradzki.composerexample.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykkosieradzki.composer.core.event.StateFlowEvent
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.composer.navigation.NavigationManager
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
    UiStateManager by UiStateManager.delegate(initialState = UiState.Loading),
    NavigationManager by NavigationManager.delegate() {

    val coin: MutableStateFlow<CoinResponse?> = MutableStateFlow(null)
    val testEvent = StateFlowEvent<Int>()

    fun initialize(coinId: String) {
        viewModelScope.launchWithExceptionHandler(
            onFailure = {
                Log.e("CoinDetailsViewModel", "Coin details fetch error", it)
                updateUiStateToFailure(it)
            }
        ) {
            val coinDetails = coinRepository.getCoinDetails(coinId)
            coin.update { coinDetails }
            updateUiStateToSuccess()
        }
    }

    fun onBackArrowClicked() {
        navigateBack()
    }
}