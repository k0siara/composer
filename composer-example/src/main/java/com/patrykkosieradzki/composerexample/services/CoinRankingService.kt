package com.patrykkosieradzki.composerexample.services

import com.patrykkosieradzki.composerexample.model.CoinDetailsResponse
import com.patrykkosieradzki.composerexample.model.CoinRankingApiResponse
import com.patrykkosieradzki.composerexample.model.CoinsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinRankingService {
    @GET("coins")
    suspend fun getCoins(): CoinRankingApiResponse<CoinsResponse>

    @GET("coin/{coinId}")
    suspend fun getCoinDetails(
        @Path("coinId") coinId: String
    ): CoinRankingApiResponse<CoinDetailsResponse>
}