package com.patrykkosieradzki.composerexample.navigation

object MainDirections {
    val Default = object : NavigationAction {
        override val destination = ""
    }
    val root = object : NavigationAction {
        override val destination = "home-root"
    }
    val home = object : NavigationAction {
        override val destination = "home"
    }
}