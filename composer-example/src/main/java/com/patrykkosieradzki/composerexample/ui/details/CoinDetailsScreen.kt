package com.patrykkosieradzki.composerexample.ui.details

import android.widget.TextView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.patrykkosieradzki.composer.composables.UiStateView
import com.patrykkosieradzki.composer.utils.asLifecycleAwareState
import com.patrykkosieradzki.composerexample.R
import com.patrykkosieradzki.composerexample.utils.toNullableString

@Composable
fun CoinDetailsScreen(
    viewModel: CoinDetailsViewModel
) {
    val coinDetails by viewModel.coin.asLifecycleAwareState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Crypto app") },
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackArrowClicked) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        UiStateView(viewModel) {
            coinDetails?.let {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        Image(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(top = 20.dp),
                            painter = rememberImagePainter(it.iconUrl, LocalImageLoader.current),
                            contentDescription = null
                        )

                        Text(
                            modifier = Modifier.padding(top = 20.dp),
                            text = it.symbol.toNullableString()
                        )
                        Text(text = it.name.toNullableString())

                        AndroidView(
                            modifier = Modifier.padding(top = 20.dp),
                            factory = { context ->
                            TextView(context).apply {
                                text = HtmlCompat.fromHtml(
                                    it.description.toNullableString(),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY)
                            }
                        }
                        )
                    }
                }
            }
        }
    }
}
