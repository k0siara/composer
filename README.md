# Composer

🎹 Jetpack Compose Framework for simple state and event management.

[![Android CI](https://github.com/k0siara/composer/actions/workflows/android.yml/badge.svg)](https://github.com/k0siara/composer/actions/workflows/android.yml)
[![License](https://img.shields.io/github/license/k0siara/composer.svg)](https://www.apache.org/licenses/LICENSE-2.0)
<!-- [![Maven Central](https://img.shields.io/maven-central/v/com.patrykkosieradzki/composer.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.patrykkosieradzki%22%20AND%20a:%composer%22) -->

![Jetpack Compose Framework for simple state and event management](/assets/composer_banner.png)

Bored of writing boilerplate code in every new Android project to handle states, events, effects, etc?

Just use Composer!

You're probably familiar with declaring Ui states for every screen in your app. Most of the time it was just a sealed class, for example:
```kotlin
sealed class HomeState {
    object Initial : HomeState()
    object Loading : HomeState()
    data class Success(...) : HomeState()
    data class Error(val throwable: Throwable) : HomeState()
    ...
}
```

Most of the screens you create will look like this. Initial, Loading, Success, Error like states. So is it worth writing it every time? Nope.

Using composer's SimpleUiState and/or ComplexUiState gives you access to these predefined states for every screen:

- Loading
- Retrying
- SwipeRefreshing
- Success
- Failure
- SwipeRefreshingFailure

This should cover most of the cases for you. If not, you can create custom states for a specific screen/case/etc.

## Quickstart

Imagine you want to create a new feature (let's say it's one screen for now).

```kotlin
class HomeViewModel : ViewModel {
    // ViewModel logic
}
```

### State handling

You've created a new ViewModel and you want to hold the screen's state inside. With composer it is really easy.

#### SimpleUiState

```kotlin
class HomeViewModel : ViewModel(),
    SimpleUiStateManager by SimpleUiStateManagerImpl(
        initialState = SimpleUiState.Loading
    ) {
    // ViewModel logic
}
```

Next, create a composable function that will present the state, based on the ComposerViewModel:

```kotlin
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel() // use your DI to get the ViewModel
) {
    SimpleUiStateView(homeViewModel) {
        HomeScreenSuccessStateView()
    }
}
```

## How to include in your project

[![Maven Central](https://img.shields.io/maven-central/v/com.patrykkosieradzki/composer.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.patrykkosieradzki%22%20AND%20a:%composer%22)

``` groovy
dependencies {
    implementation "com.patrykkosieradzki:composer:1.0.0"
}

```

## SNAPSHOT
[![Composer](https://img.shields.io/static/v1?label=snapshot&message=Composer&logo=apache%20maven&color=C71A36)](https://s01.oss.sonatype.org/content/repositories/snapshots/com/patrykkosieradzki/composer/) <br>

<details>
 <summary>See how to import the snapshot</summary>

### Including the SNAPSHOT
Snapshots of the current development version of Composer are available, which track [the latest versions](https://s01.oss.sonatype.org/content/repositories/snapshots/com/patrykkosieradzki/composer/).

To import snapshot versions on your project, add the code snippet below on your gradle file.
```Gradle
repositories {
   maven { url 'https://s01.oss.sonatype.org/content/repositories/snapshots/' }
}
```

Next, add the below dependency to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.patrykkosieradzki:composer:1.0.0-SNAPSHOT"
}
```

</details>

License
=======

    Copyright 2021 Patryk Kosieradzki.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
