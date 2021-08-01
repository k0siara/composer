package com.patrykkosieradzki.composerexample.services

import com.patrykkosieradzki.composerexample.model.CoinsResponse
import retrofit2.http.GET

interface CoinRankingService {
    @GET("coins")
    suspend fun getCoins(): CoinsResponse
}