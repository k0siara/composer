package com.patrykkosieradzki.composer.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.patrykkosieradzki.composer.core.ComposerEffectHandler

fun <T> MutableLiveData<T>.fireChange(t: T) {
    value = t
}

inline val <T> MutableLiveData<T>.readOnly: LiveData<T>
    get() = this

inline val <T> LiveData<T>.valueNN
    get() = this.value!!

fun <T> ComposerEffectHandler<T>.fireEffect(effect: T) {
    this.value = effect
}

fun ComposerEffectHandler<Unit>.fireEffect() {
    this.value = Unit
}