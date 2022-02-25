package com.patrykkosieradzki.composerexample.model

data class CoinDetailsResponse (
    val coin: CoinResponse
)

data class AllTimeHigh (
    val price: String,
    val timestamp: Long
)

data class Link (
    val name: String,
    val type: String,
    val url: String
)

data class Supply (
    val confirmed: Boolean,
    val total: String,
    val circulating: String
)