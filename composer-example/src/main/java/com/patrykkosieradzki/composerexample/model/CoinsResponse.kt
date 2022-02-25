package com.patrykkosieradzki.composerexample.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinsResponse(
    @Json(name = "stats") val stats: Stats?,
    @Json(name = "coins") val coins: List<CoinResponse>
)

@JsonClass(generateAdapter = true)
data class Stats(
    @Json(name = "total") val total: Long,
    @Json(name = "totalMarkets") val totalMarkets: Long,
    @Json(name = "totalExchanges") val totalExchanges: Long,
    @Json(name = "totalMarketCap") val totalMarketCap: String,
    @Json(name = "total24HVolume") val total24HVolume: String?
)
