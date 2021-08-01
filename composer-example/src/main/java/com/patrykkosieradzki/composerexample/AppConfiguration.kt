package com.patrykkosieradzki.composerexample

interface AppConfiguration {
    val apiUrl: String
}

class ComposerAppConfiguration : AppConfiguration {
    override val apiUrl: String = "https://api.coinranking.com/v2/"
}