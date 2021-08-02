package com.patrykkosieradzki.composerexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.patrykkosieradzki.composer.composables.asLifecycleAwareState
import com.patrykkosieradzki.composerexample.navigation.ComposerNavigator
import com.patrykkosieradzki.composerexample.navigation.MainDirections
import com.patrykkosieradzki.composerexample.navigation.NavigationAction
import com.patrykkosieradzki.composerexample.ui.home.HomeScreen
import com.patrykkosieradzki.composerexample.ui.theme.ComposerExampleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var navigator: ComposerNavigator
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposerExampleTheme {
                val navController = rememberNavController()

                val lifecycleOwner = LocalLifecycleOwner.current
                val navigatorState by navigator.navActions.asLifecycleAwareState(
                    lifecycleOwner = lifecycleOwner,
                    initialState = MainDirections.root
                )
                LaunchedEffect(navigatorState) {
                    navController.navigate(navigatorState.destination)
                }

                NavHost(
                    navController = navController,
                    startDestination = MainDirections.root.destination
                ) {
                    navigation(
                        startDestination = MainDirections.home.destination,
                        route = MainDirections.root.destination
                    ) {
                        composable(MainDirections.home.destination) {
                            Scaffold {
                                HomeScreen(
                                    hiltViewModel(navController.getBackStackEntry(MainDirections.home.destination))
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
