package com.patrykkosieradzki.composerexample.repositories

import com.patrykkosieradzki.composerexample.model.Coin
import com.patrykkosieradzki.composerexample.services.CoinRankingService

interface CoinRepository {
    suspend fun getCoins(): List<Coin>
}

class CoinApiRepository(
    private val coinRankingService: CoinRankingService
) : CoinRepository {
    override suspend fun getCoins() = coinRankingService.getCoins().data.coins
}