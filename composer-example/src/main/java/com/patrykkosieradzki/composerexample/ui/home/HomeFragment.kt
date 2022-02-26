package com.patrykkosieradzki.composerexample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.patrykkosieradzki.composer.dialog.ComposerDialogComposable
import com.patrykkosieradzki.composer.navigation.observeNavigation
import com.patrykkosieradzki.composerexample.utils.composeView
import com.patrykkosieradzki.composerexample.utils.getImageLoaderFromMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return composeView(
            imageLoader = getImageLoaderFromMainActivity()
        ) {
            HomeScreen(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeNavigation(this)
        viewModel.initialize()
    }
}
