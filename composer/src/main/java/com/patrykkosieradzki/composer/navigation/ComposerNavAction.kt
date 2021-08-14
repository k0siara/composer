package com.patrykkosieradzki.composer.navigation

import android.os.Parcelable
import androidx.navigation.NavOptions

data class ComposerNavAction(
    val destination: String,
    val parcelables: Map<String, Parcelable> = emptyMap(),
    val navOptions: NavOptions = NavOptions.Builder().build()
)