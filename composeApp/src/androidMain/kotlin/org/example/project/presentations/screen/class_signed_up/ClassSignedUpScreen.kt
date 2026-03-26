package org.example.project.presentations.screen.class_signed_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.class_signed_up.components.ClassSignedUpContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ClassSignUpScreen(
    viewModel: ClassSignedUpViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ClassSignedUpContent(
        uiState = uiState,
        color = LocalExtendedColors.current,
        onConfirm = {},
        onBack = {}
    )
}