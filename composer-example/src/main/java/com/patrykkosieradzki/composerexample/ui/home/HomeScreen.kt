package com.patrykkosieradzki.composerexample.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykkosieradzki.composer.composables.UiStateView
import com.patrykkosieradzki.composerexample.model.Coin
import java.math.RoundingMode

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel
) {
    UiStateView(homeViewModel) {
        LazyColumn {
            items(it.coins) { coin ->
                CoinListItem(
                    coin = coin,
                    onClick = homeViewModel::onCoinClicked
                )
            }
        }
    }
}

@Composable
fun CoinListItem(
    coin: Coin,
    onClick: (Coin) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = { onClick.invoke(coin) }
            )
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = coin.name)
            Text(
                text = coin.marketCap.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString()
            )
        }
        Text(text = coin.price.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString())
    }
}