package com.patrykkosieradzki.composerexample.ui.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykkosieradzki.composer.composables.ComposerView

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    ComposerView(viewModel = homeViewModel) {
        Text(text = "Something")
    }
}