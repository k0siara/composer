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
package com.patrykkosieradzki.composer.core.state

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun <T> AsyncView(
    modifier: Modifier = Modifier,
    asyncProvider: () -> Async<T>,
    renderOnUninitialized: (@Composable () -> Unit)? = null,
    renderOnLoading: (@Composable (T?) -> Unit)? = null,
    renderOnEmpty: (@Composable (T?) -> Unit)? = null,
    renderOnFailure: (@Composable (Throwable, T?) -> Unit)? = null,
    renderOnSuccess: (@Composable (T?) -> Unit)? = null
) {
    val async = asyncProvider()

    Crossfade(
        modifier = Modifier.then(modifier),
        targetState = async.javaClass.kotlin,
        animationSpec = tween(300)
    ) { animatedAsync ->
        when (animatedAsync) {
            Async.Uninitialized::class -> {
                renderOnUninitialized?.invoke()
            }
            Async.Loading::class -> {
                renderOnLoading?.invoke(async.invoke())
            }
            Async.Success::class -> {
                renderOnSuccess?.invoke(async.invoke())
            }
            Async.Empty::class -> {
                renderOnEmpty?.invoke(async.invoke())
            }
            Async.Fail::class -> {
                if (async is Async.Fail) {
                    renderOnFailure?.invoke(async.error, async())
                }
            }
        }
    }
}
