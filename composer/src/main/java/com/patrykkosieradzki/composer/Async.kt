package com.patrykkosieradzki.composer

sealed class Async<out T>(val complete: Boolean, val shouldLoad: Boolean, private val value: T?) {
    open operator fun invoke(): T? = value

    object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true, value = null),
        Incomplete

    data class Loading<out T>(private val value: T? = null) :
        Async<T>(complete = false, shouldLoad = false, value = value), Incomplete

    data class Success<out T>(private val value: T) :
        Async<T>(complete = true, shouldLoad = false, value = value)

    data class Fail<out T>(val error: Throwable, private val value: T? = null) :
        Async<T>(complete = true, shouldLoad = true, value = value)
}

interface Incomplete
