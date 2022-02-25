package com.patrykkosieradzki.composerexample

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.patrykkosieradzki.composer.toast.ToastManager
import com.patrykkosieradzki.composer.toast.observeToastEffects
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var toastManager: ToastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toastManager.observeToastEffects(this)
    }
}
