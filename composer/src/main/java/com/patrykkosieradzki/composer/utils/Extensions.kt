package com.patrykkosieradzki.composer.utils

fun <T> T.orElse(t: T): T {
    return this ?: t
}
