package com.patrykkosieradzki.composer.composables.snackbar

import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.ViewModel
import com.patrykkosieradzki.composer.utils.lifecycleAwareState
import com.patrykkosieradzki.composer.delegates.DisplaysSnackbar

@Composable
fun HandleSnackbarIfSupported(
    viewModel: ViewModel,
    scaffoldState: ScaffoldState
) {
    if (viewModel is DisplaysSnackbar) {
        val lifecycleOwner = LocalLifecycleOwner.current
        val snackbarState by lifecycleAwareState(
            lifecycleOwner = lifecycleOwner,
            flow = viewModel.snackbarState,
            initialState = viewModel.initialSnackbarState
        )

        if (snackbarState.isShown) {
            LaunchedEffect(scaffoldState.snackbarHostState) {
                when (scaffoldState.snackbarHostState.showSnackbar(snackbarState.message)) {
                    SnackbarResult.Dismissed -> viewModel.dismissSnackbar()
                    SnackbarResult.ActionPerformed -> {
                    }
                    else -> {
                    }
                }
            }
        }
    }
}
