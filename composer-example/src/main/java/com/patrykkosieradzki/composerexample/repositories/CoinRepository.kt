package com.patrykkosieradzki.composerexample.repositories

import com.patrykkosieradzki.composerexample.model.CoinResponse
import com.patrykkosieradzki.composerexample.services.CoinRankingService

interface CoinRepository {
    suspend fun getCoins(): List<CoinResponse>
    suspend fun getCoinDetails(id: String): CoinResponse
}

class CoinApiRepository(
    private val coinRankingService: CoinRankingService
) : CoinRepository {
    override suspend fun getCoins() = coinRankingService.getCoins().data.coins
    override suspend fun getCoinDetails(id: String): CoinResponse {
        return coinRankingService.getCoinDetails(id).data.coin
    }
}