object Versions {
    const val kotlin = "1.5.10"
    const val gradlePlugin = "7.0.0"
    const val compose = "1.0.0"
    const val lifecycle = "2.4.0-alpha02"
}

object Deps {
    const val composeFoundation = "androidx.compose.foundation:foundation:${Versions.compose}"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }
}