package com.patrykkosieradzki.composerexample.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
                CoinListItem(coin)
            }
        }
    }
}

@Composable
fun CoinListItem(coin: Coin) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(20.dp)
    ) {
        Column {
            Text(text = coin.name)
            Text(text = coin.marketCap.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString())
        }
        Text(text = coin.price.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toString())
    }
}