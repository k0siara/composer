package com.patrykkosieradzki.composerexample.ui.home

import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.core.state.UiState
import com.patrykkosieradzki.composer.core.state.UiStateManager
import com.patrykkosieradzki.composer.dialog.DialogManager
import com.patrykkosieradzki.composer.dialog.DialogManagerImpl
import com.patrykkosieradzki.composer.navigation.NavigationManager
import com.patrykkosieradzki.composer.navigation.NavigationManagerImpl
import com.patrykkosieradzki.composer.toast.ToastManager
import com.patrykkosieradzki.composerexample.repositories.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
    private val toastManager: ToastManager
) : ViewModel(),
    UiStateManager by UiStateManager.delegate(initialState = UiState.Loading),
    NavigationManager by NavigationManagerImpl(),
    DialogManager by DialogManagerImpl() {

    fun initialize() {
//        if (currentState is ComplexUiState.Loading) {
//            toastManager.showToast("Hello!")
//            loadCoins()
//        }
    }

    private fun loadCoins() {
//        launchWithExceptionHandler(
//            onFailure = { throwable ->
//                Log.e("HomeViewModel", "Coin fetch error", throwable)
//                updateUiStateToFailure(throwable)
//            }
//        ) {
//            val coins = coinRepository.getCoins()
//            updateComplexUiStateToSuccess { it.copy(coins = coins) }
//        }
    }

    fun onCoinClicked(coinId: String?) {
        if (coinId != null) {
            navigateTo(HomeFragmentDirections.toCoinDetailsFragment(coinId))
        } else {
            toastManager.showToast("Coin details unavailable")
        }
    }
}
