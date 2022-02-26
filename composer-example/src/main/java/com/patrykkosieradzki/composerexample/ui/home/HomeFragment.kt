package com.patrykkosieradzki.composerexample.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector
import com.patrykkosieradzki.composer.dialog.ComposerDialog
import com.patrykkosieradzki.composer.dialog.DialogManager
import com.patrykkosieradzki.composer.dialog.DialogManagerObserver
import com.patrykkosieradzki.composer.navigation.observeNavigation
import com.patrykkosieradzki.composer.utils.observeInLifecycle
import com.patrykkosieradzki.composerexample.utils.composeView
import com.patrykkosieradzki.composerexample.utils.getImageLoaderFromMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach

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
            DialogManagerObserver(viewModel) {

            }

            HomeScreen(viewModel)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeNavigation(this)
        viewModel.initialize()
    }
}


