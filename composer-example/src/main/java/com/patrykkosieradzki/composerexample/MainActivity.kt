package com.patrykkosieradzki.composerexample

import androidx.fragment.app.FragmentActivity
import com.patrykkosieradzki.composer.toast.ComposerToastManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var toastManager: ComposerToastManager
}
