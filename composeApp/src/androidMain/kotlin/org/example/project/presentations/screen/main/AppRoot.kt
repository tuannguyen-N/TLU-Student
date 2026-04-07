package org.example.project.presentations.screen.main

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.example.project.di.AppContainer
import org.example.project.presentations.navigation.AppNavGraph
import org.example.project.presentations.theme.AppTheme
import org.example.project.presentations.theme.LocalExtendedColors

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("No AppContainer provided")
}

@Composable
fun AppRoot(
    resetAppData: () -> Unit
) {
    AppTheme {
        Surface(
            color = Color.White
        ) {
            AppNavGraph(
                resetAppData = resetAppData
            )
        }
    }
}