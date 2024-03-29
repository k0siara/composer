/*
 * Copyright (C) 2022 Patryk Kosieradzki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patrykkosieradzki.composer.loading

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
            loadingTime.timeInMs - (System.currentTimeMillis() - startTime.value)
        if (loadingTimeLeft > 0) delay(loadingTimeLeft)
    }

    sealed interface LoadingTime {
        val timeInMs: Long

        object DefaultLoadTime : LoadingTime {
            override val timeInMs: Long = 1000L
        }

        object DefaultLoadFailTime : LoadingTime {
            override val timeInMs: Long = 500L
        }
    }
}
