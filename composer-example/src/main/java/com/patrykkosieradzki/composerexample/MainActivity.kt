package com.patrykkosieradzki.composerexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.patrykkosieradzki.composer.core.ComposerLifecycleAwareFlowCollector
import com.patrykkosieradzki.composer.navigation.ComposerNavigationHandler
import com.patrykkosieradzki.composer.navigation.ComposerNavigator
import com.patrykkosieradzki.composerexample.navigation.MainActions
import com.patrykkosieradzki.composerexample.ui.home.HomeScreen
import com.patrykkosieradzki.composerexample.ui.theme.ComposerExampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @Inject
    lateinit var navigator: ComposerNavigator
    
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposerExampleTheme {
                val navController = rememberNavController()

                ComposerNavigationHandler(
                    composerNavigator = navigator,
                    navHostController = navController
                )

                NavHost(
                    navController = navController,
                    startDestination = MainActions.root.destination
                ) {
                    navigation(
                        startDestination = MainActions.home.destination,
                        route = MainActions.root.destination
                    ) {
                        composable(MainActions.home.destination) {
                            Scaffold {
                                HomeScreen(
                                    hiltViewModel(navController.getBackStackEntry(MainActions.home.destination))
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
