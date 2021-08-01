[![build](https://github.com/k0siara/composer/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/k0siara/composer/actions/workflows/android.yml)

Composer
========

Composer is a Jetpack Compose Framework for simple state and event management. 


First, define a ComposerViewModel and ComposerUIStateData
```kotlin

class HomeViewModel : ComposerViewModel<HomeStateData>(
    initialState = ComposerUIState.Success(HomeStateData())
) {
    // ViewModel logic
}

data class HomeStateData(
    val title: String = "Home Screen"
) : ComposerUIStateData
```

Installation
=======
Comming soon to maven central

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
