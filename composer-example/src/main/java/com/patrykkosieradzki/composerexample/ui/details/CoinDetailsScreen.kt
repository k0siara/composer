package com.patrykkosieradzki.composerexample.ui.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.patrykkosieradzki.composer.composables.SimpleUiStateView

@Composable
fun CoinDetailsScreen(
    coinDetailsViewModel: CoinDetailsViewModel
) {
    SimpleUiStateView(coinDetailsViewModel) {
        Text(text = "Details")
    }
}