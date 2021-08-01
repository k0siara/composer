package com.patrykkosieradzki.composer.buildsrc

object Versions {
    const val kotlin = "1.5.10"
    const val compose = "1.0.0"
    const val androidGradlePlugin = "7.0.0"
    const val lifecycle = "2.4.0-alpha02"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val activityCompose = "androidx.activity:activity-compose:1.3.0"
    const val material = "com.google.android.material:material:1.4.0"

    object Kotlin {
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    }

    object Coroutines {
        private const val version = "1.5.1"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        object Compose {
            const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
            const val compiler = "androidx.compose.compiler:compiler:${Versions.compose}"
            const val layout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
            const val ui = "androidx.compose.ui:ui:${Versions.compose}"
            const val uiUtil = "androidx.compose.ui:ui-util:${Versions.compose}"
            const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
            const val material = "androidx.compose.material:material:${Versions.compose}"
            const val animation = "androidx.compose.animation:animation:${Versions.compose}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:${Versions.compose}"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        }

        object Lifecycle {
            const val lifeCycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
            const val lifeCycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        }

        object Test {
            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"

            object Ext {
                const val junit = "androidx.test.ext:junit:1.1.3"
            }
        }
    }

    object Koin {
        private const val version = "3.1.2"
        const val core = "io.insert-koin:koin-core:$version"
        const val compose = "io.insert-koin:koin-androidx-compose:$version"
    }

    object SquareUp {
        const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:1.12.0"
        const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:2.5.0"
        const val moshiAdapters = "com.squareup.moshi:moshi-adapters:1.7.0"
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    }

    object JUnit {
        const val junit = "junit:junit:4.+"
    }
}