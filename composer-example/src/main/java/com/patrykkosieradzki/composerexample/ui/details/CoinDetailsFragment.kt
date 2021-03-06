package com.patrykkosieradzki.composerexample.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.patrykkosieradzki.composer.navigation.observeNavigation
import com.patrykkosieradzki.composer.navigation.registerBackNavigationHandler
import com.patrykkosieradzki.composerexample.utils.composeView
import com.patrykkosieradzki.composerexample.utils.getImageLoaderFromMainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailsFragment : Fragment() {

    private val args: CoinDetailsFragmentArgs by navArgs()

    private val viewModel: CoinDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return composeView(
            imageLoader = getImageLoaderFromMainActivity()
        ) {
            CoinDetailsScreen(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.observeNavigation(this)
        registerBackNavigationHandler()
        viewModel.initialize(args.coinId)
    }
}
