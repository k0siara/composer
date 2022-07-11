package com.patrykkosieradzki.composer.buildsrc

object Versions {
    internal const val ANDROID_GRADLE_PLUGIN = "7.2.0"
    internal const val ANDROID_GRADLE_SPOTLESS = "6.3.0"
    internal const val GRADLE_NEXUS_PUBLISH_PLUGIN = "1.1.0"

    internal const val COMPOSE = "1.2.0-alpha04"
    internal const val LIFECYCLE = "2.4.0-alpha02"

    internal const val KOTLIN = "1.6.21"
    internal const val COROUTINES_CORE = "1.6.2"

    internal const val JUNIT = "4.13.2"
}

object Dependencies {
    const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val spotlessGradlePlugin =
        "com.diffplug.spotless:spotless-plugin-gradle:${Versions.ANDROID_GRADLE_SPOTLESS}"
    const val gradleNexusPublishPlugin =
        "io.github.gradle-nexus:publish-plugin:${Versions.GRADLE_NEXUS_PUBLISH_PLUGIN}"

    const val activityCompose = "androidx.activity:activity-compose:1.3.0"
    const val material = "com.google.android.material:material:1.4.0"

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.COROUTINES_CORE}"
    }

    object AndroidX {
        const val appCompat = "androidx.appcompat:appcompat:1.3.1"
        const val coreKtx = "androidx.core:core-ktx:1.6.0"

        object Compose {
            const val foundation = "androidx.compose.foundation:foundation:${Versions.COMPOSE}"
            const val compiler = "androidx.compose.compiler:compiler:${Versions.COMPOSE}"
            const val layout = "androidx.compose.foundation:foundation-layout:${Versions.COMPOSE}"
            const val ui = "androidx.compose.ui:ui:${Versions.COMPOSE}"
            const val uiUtil = "androidx.compose.ui:ui-util:${Versions.COMPOSE}"
            const val runtime = "androidx.compose.runtime:runtime:${Versions.COMPOSE}"
            const val material = "androidx.compose.material:material:${Versions.COMPOSE}"
            const val animation = "androidx.compose.animation:animation:${Versions.COMPOSE}"
            const val tooling = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:${Versions.COMPOSE}"
            const val iconsExtended =
                "androidx.compose.material:material-icons-extended:${Versions.COMPOSE}"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:${Versions.COMPOSE}"
        }

        object Lifecycle {
            const val lifeCycleViewModelKtx =
                "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
            const val lifeCycleRuntimeKtx =
                "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.LIFECYCLE}"
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