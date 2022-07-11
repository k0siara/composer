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
package com.patrykkosieradzki.composer.core

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.patrykkosieradzki.composer.composables.CenteredColumn

class Composer private constructor() {
    class UiStateRenderConfig {
        companion object {
            var defaultLoadingComposable: @Composable () -> Unit = {
                DefaultLoadingView()
            }
            var defaultRetryingComposable: @Composable () -> Unit = {
                DefaultRetryingView()
            }
            var defaultSwipeRefreshingComposable: @Composable () -> Unit = {
                DefaultSwipeRefreshingView()
            }
            var defaultSuccessComposable: @Composable () -> Unit = {
                DefaultSuccessView()
            }
            var defaultFailureComposable: @Composable (error: Throwable) -> Unit = { error ->
                DefaultFailureView(error)
            }
            var defaultSwipeRefreshFailureComposable:
                @Composable (error: Throwable) -> Unit = { error ->
                    DefaultSwipeRefreshFailureView(error)
                }
        }
    }

    class AsyncRenderConfig {
        companion object {
            var defaultUninitializedComposable: @Composable () -> Unit = {
                DefaultUninitializedView()
            }
            var defaultLoadingComposable: @Composable () -> Unit = {
                DefaultLoadingView()
            }
            var defaultSuccessComposable: @Composable () -> Unit = {
                DefaultSuccessView()
            }
            var defaultFailComposable: @Composable (error: Throwable) -> Unit = { error ->
                DefaultFailureView(error)
            }
            var hideEveryAsyncOnFailure = false
        }
    }
}

@Composable
private fun DefaultRetryingView() {
    CenteredColumn {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
        Text(text = "Retrying...")
    }
}

@Composable
private fun DefaultSwipeRefreshingView() {
    CenteredColumn {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
        Text(text = "Swipe refreshing...")
    }
}

@Composable
private fun DefaultSwipeRefreshFailureView(error: Throwable) {
    CenteredColumn {
        Text(text = "Swipe refresh error occurred")
        Text(text = error.message ?: "No message available")
    }
}

@Composable
private fun DefaultUninitializedView() {
    CenteredColumn {
        Text(text = "Uninitialized")
    }
}

@Composable
private fun DefaultLoadingView() {
    CenteredColumn {
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary
        )
        Text(text = "Loading...")
    }
}

@Composable
private fun DefaultSuccessView() {
    CenteredColumn {
        Text(text = "Success state. To be implemented...")
    }
}

@Composable
private fun DefaultFailureView(error: Throwable) {
    CenteredColumn {
        Text(text = "Error occurred")
        Text(text = error.message ?: "No message available")
    }
}
