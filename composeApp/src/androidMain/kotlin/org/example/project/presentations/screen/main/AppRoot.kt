package org.example.project.presentations.screen.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import org.example.project.di.AppContainer
import org.example.project.presentations.navigation.AppNavGraph
import org.example.project.presentations.theme.AppTheme

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("No AppContainer provided")
}

@Composable
fun AppRoot() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppNavGraph()
        }
    }
}