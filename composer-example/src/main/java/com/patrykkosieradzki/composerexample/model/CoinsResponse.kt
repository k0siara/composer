package com.patrykkosieradzki.composerexample.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoinsResponse(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: Data
)

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = "stats") val stats: Stats?,
    @Json(name = "coins") val coins: List<Coin>
)

@JsonClass(generateAdapter = true)
data class Coin(
    @Json(name = "uuid") val uuid: String = "",
    @Json(name = "symbol") val symbol: String = "",
    @Json(name = "name") val name: String = "",
    @Json(name = "color") val color: String? = "",
    @Json(name = "iconUrl") val iconUrl: String? = "",
    @Json(name = "marketCap") val marketCap: String = "",
    @Json(name = "price") val price: String = "",
    @Json(name = "listedAt") val listedAt: Long = 0,
    @Json(name = "tier") val tier: Long = 0,
    @Json(name = "change") val change: String = "",
    @Json(name = "rank") val rank: Long = 0,
    @Json(name = "sparkline") val sparkline: List<String> = emptyList(),
    @Json(name = "lowVolume") val lowVolume: Boolean = false,
    @Json(name = "coinrankingURL") val coinrankingURL: String? = "",
    @Json(name = "the24HVolume") val the24HVolume: String? = "",
    @Json(name = "btcPrice") val btcPrice: String = ""
)

@JsonClass(generateAdapter = true)
data class Stats(
    @Json(name = "total") val total: Long,
    @Json(name = "totalMarkets") val totalMarkets: Long,
    @Json(name = "totalExchanges") val totalExchanges: Long,
    @Json(name = "totalMarketCap") val totalMarketCap: String,
    @Json(name = "total24HVolume") val total24HVolume: String?
)
