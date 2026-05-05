package org.example.project.presentations.screen.main

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import org.example.project.di.AppContainer
import org.example.project.presentations.navigation.AppNavGraph
import org.example.project.presentations.theme.AppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.layout.Box
import org.example.project.presentations.dialog.NoNetworkDialog
import org.example.project.data.remote.showNoNetworkDialog

val LocalAppContainer = staticCompositionLocalOf<AppContainer> {
    error("No AppContainer provided")
}

@Composable
fun AppRoot(
    resetAppData: () -> Unit
) {
    var showNoNetworkDialogState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showNoNetworkDialog.collect {
            showNoNetworkDialogState = it
        }
    }

    AppTheme {
        Surface(
            color = Color.White
        ) {
            Box {
                AppNavGraph(
                    resetAppData = resetAppData
                )
                if (showNoNetworkDialogState) {
                    NoNetworkDialog(
                        onDismiss = {
                            showNoNetworkDialog.value = false
                        }
                    )
                }
            }
        }
    }
}