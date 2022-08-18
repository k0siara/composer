package com.patrykkosieradzki.composerexample.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import com.patrykkosieradzki.composer.core.state.composables.UiStateView
import com.patrykkosieradzki.composerexample.model.CoinResponse
import com.patrykkosieradzki.composerexample.utils.toNullableString
import java.math.RoundingMode

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Crypto app") }
            )
        }
    ) {
        UiStateView(homeViewModel) {
            LazyColumn {
//                items(it.coins) { coin ->
//                    CoinListItem(
//                        coin = coin,
//                        onClick = homeViewModel::onCoinClicked
//                    )
//                }
            }
        }
    }
}

@Composable
fun CoinListItem(
    coin: CoinResponse,
    onClick: (String?) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = { onClick.invoke(coin.uuid) }
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                modifier = Modifier.size(50.dp),
                painter = rememberImagePainter(coin.iconUrl, LocalImageLoader.current),
                contentDescription = null
            )
            Column {
                Text(text = coin.symbol.toNullableString())
                Text(text = coin.name.toNullableString())
            }
        }
        Text(text = "$ ${coin.price.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN)}")
    }
}