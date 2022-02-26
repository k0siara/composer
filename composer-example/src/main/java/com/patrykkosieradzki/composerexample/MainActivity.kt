package com.patrykkosieradzki.composerexample

import android.os.Bundle
import androidx.compose.material.Text
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.util.CoilUtils
import com.patrykkosieradzki.composer.core.Composer
import com.patrykkosieradzki.composer.toast.ToastManager
import com.patrykkosieradzki.composer.toast.observeToastEffects
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @Inject
    lateinit var toastManager: ToastManager

    lateinit var imageLoader: ImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupImageLoader()

        toastManager.observeToastEffects(this)

        Composer.UiStateRenderConfig.defaultFailureComposable = {
            Text(text = "JEBAC PUTINA")
        }
    }

    private fun setupImageLoader() {
        imageLoader = ImageLoader.Builder(this)
            .componentRegistry {
                add(SvgDecoder(this@MainActivity))
            }
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }
            .build()
    }
}
