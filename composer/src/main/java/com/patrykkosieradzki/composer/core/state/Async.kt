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

sealed class Async<out T>(
    val complete: Boolean,
    val shouldLoad: Boolean,
    private val value: T?
) {
    open operator fun invoke(): T? = value

    object Uninitialized :
        Async<Nothing>(complete = false, shouldLoad = true, value = null),
        Incomplete

    data class Loading<out T>(private val value: T? = null) :
        Async<T>(complete = false, shouldLoad = false, value = value), Incomplete

    data class Success<out T>(private val value: T) :
        Async<T>(complete = true, shouldLoad = false, value = value)

    data class Empty<out T>(private val value: T? = null) :
        Async<T>(complete = true, shouldLoad = false, value = value)

    data class Fail<out T>(val error: Throwable, private val value: T? = null) :
        Async<T>(complete = true, shouldLoad = true, value = value)
}

interface Incomplete

fun <T, K> Async<T>.map(map: (T?) -> K): Async<K> {
    return when (this) {
        is Async.Uninitialized -> Async.Uninitialized
        is Async.Loading -> Async.Loading(map(invoke()))
        is Async.Success -> Async.Success(map(invoke()))
        is Async.Empty -> Async.Empty(map(invoke()))
        is Async.Fail -> Async.Fail(error, map(invoke()))
    }
}
