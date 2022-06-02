package com.patrykkosieradzki.composer.core.load

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LoadingSession {
    private val startTime = MutableStateFlow(System.currentTimeMillis())

    fun restartLoading() {
        startTime.update { System.currentTimeMillis() }
    }

    suspend fun ensureMinimumLoadingTime(
        loadingTime: LoadingTime
    ) {
        val loadingTimeLeft =
            loadingTime.timeInMs.toLong() - (System.currentTimeMillis() - startTime.value)
        if (loadingTimeLeft > 0) delay(loadingTimeLeft)
    }

    sealed class LoadingTime(val timeInMs: Int) {
        object Short : LoadingTime(300)
        object Long : LoadingTime(1000)
    }
}
