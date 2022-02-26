package com.patrykkosieradzki.composerexample.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.state.complex.ComplexUiStateManagerImpl
import com.patrykkosieradzki.composer.core.state.complex.ComplexUiState
import com.patrykkosieradzki.composer.core.state.complex.ComplexUiStateManager
import com.patrykkosieradzki.composer.dialog.ComposerDialog
import com.patrykkosieradzki.composer.dialog.DialogManager
import com.patrykkosieradzki.composer.dialog.DialogManagerImpl
import com.patrykkosieradzki.composer.extensions.launchWithExceptionHandler
import com.patrykkosieradzki.composer.navigation.NavigationManager
import com.patrykkosieradzki.composer.navigation.NavigationManagerImpl
import com.patrykkosieradzki.composer.toast.ToastManager
import com.patrykkosieradzki.composerexample.model.CoinResponse
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import com.patrykkosieradzki.composerexample.ui.home.HomeViewModel.HomeStateData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val toastManager: ToastManager
) : ViewModel(),
    ComplexUiStateManager<HomeStateData> by ComplexUiStateManagerImpl(
        initialState = ComplexUiState.Loading(HomeStateData()),
        HomeStateData::class
    ),
    NavigationManager by NavigationManagerImpl(),
    DialogManager by DialogManagerImpl() {

    fun initialize() {
        if (currentState is ComplexUiState.Loading) {
            toastManager.showToast("Hello!")
            loadCoins()
        }
    }

    private fun loadCoins() {
        launchWithExceptionHandler(
            onFailure = { throwable ->
                Log.e("HomeViewModel", "Coin fetch error", throwable)
                updateUiStateToFailure(throwable)
            }
        ) {
            val coins = coinRepository.getCoins()
            updateComplexUiStateToSuccess { it.copy(coins = coins) }
        }
    }

    fun onCoinClicked(coinId: String?) {
        if (coinId != null) {
            navigateTo(HomeFragmentDirections.toCoinDetailsFragment(coinId))
        } else {
            toastManager.showToast("Coin details unavailable")
        }
    }

    data class HomeStateData(
        val coins: List<CoinResponse> = emptyList()
    )
}
