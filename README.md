[![build](https://github.com/k0siara/composer/actions/workflows/android.yml/badge.svg?branch=master)](https://github.com/k0siara/composer/actions/workflows/android.yml)

Composer
========

Composer is a Jetpack Compose Framework for simple state and event management. 

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

Most of the screens you create will look like this. Initial, Loading, Success, Error like states. So is it worth writing it every time? 

NO, JUST USE ComposerUiState and you're ready to go.

Using ComposerUiState gives you access to these predefined states for every screen:

- Loading
- Retrying
- SwipeRefreshing
- Success
- Failure
- SwipeRefreshingFailure

This should cover most of the cases for you. If not, you can create custom states for a specific screen/case/etc.

## Quickstart

First, define a ComposerViewModel and ComposerUIStateData, that will hold your screen data, regardless of the UiState that is has right now:

```kotlin
class HomeViewModel : ComposerViewModel<HomeStateData>(
    initialState = ComposerUiState.Loading(HomeStateData())
) {
    // ViewModel logic
}

data class HomeStateData(
    val title: String = "",
    val subtitle: String = "",
    val additionalInfo: String = ""
) : ComposerUIStateData
```

Next, create a composable function that will present the state, based on the ComposerViewModel:

```kotlin
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel() // use your DI to get the ViewModel
) {
    ComposerUiStateView(viewModel = homeViewModel) { data ->
        HomeScreenSuccessStateView(data)
    }
}

@Composable
private fun HomeScreenSuccessStateView(
    data: HomeStateData
) {
    // Define your UI...
}
```

## Updating state

Now, let's say you want to change your state data and update it in your composable. How do we do that? It's easy:

```kotlin
class HomeViewModel : ComposerViewModel<HomeStateData>(
    initialState = ComposerUiState.Loading(HomeStateData())
) {
    fun onButtonClicked() {
        updateUiState { 
            ComposerUiState.Success(
                HomeStateData(
                    title = "Hello, world!
                )
            ) 
        }
    }
    
    // or just...
    
    fun onSecondButtonClicked() {
        updateUiStateToSuccess { 
            it.copy(
                title = "Hello, world!
            )
        }
    }
}
```

## Handling events

If you're a MVI fan then you can easily handle events with composer. Just define events for your ViewModel and implement ComposerEventHandler:

```kotlin
class HomeViewModel : ComposerViewModel<HomeStateData>(
    initialState = ComposerUiState.Loading(HomeStateData())
), ComposerEventHandler<HomeEvent> {

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.ButtonClick -> onButtonClicked()
        }
    }

    private fun onButtonClicked() {
        updateUiStateToSuccess { 
            it.copy(
                title = "Hello, world!
            )
        }
    }
}

sealed class HomeEvent : ComposerUiEvent {
    object ButtonClick : HomeEvent()
}
```

Then, simply use it in your composables:

```kotlin
@Composable
private fun HomeScreenSuccessStateView(
    data: HomeStateData,
    eventHandler: ComposerEventHandler<HomeEvent>
) {
    Button(
        onClick = { 
            eventHandler.handleEvent(HomeEvent.ButtonClick) 
        },
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = Color.Red
        )
    ) {
        Text("Button")
    }
}
```

Installation
=======
Coming soon to maven central

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
