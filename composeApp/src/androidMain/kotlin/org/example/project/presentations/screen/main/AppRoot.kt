package org.example.project.presentations.screen.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import org.example.project.presentations.navigation.AppNavGraph
import org.example.project.presentations.theme.AppTheme

@Composable
fun AppRoot() {
    AppTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            AppNavGraph()
        }
    }
}