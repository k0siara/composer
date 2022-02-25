package com.patrykkosieradzki.composerexample.model

import com.squareup.moshi.Json

data class CoinRankingApiResponse<DATA>(
    @Json(name = "status") val status: String,
    @Json(name = "data") val data: DATA
)
