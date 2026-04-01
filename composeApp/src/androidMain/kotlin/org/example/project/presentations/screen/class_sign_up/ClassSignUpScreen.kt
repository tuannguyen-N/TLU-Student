package org.example.project.presentations.screen.class_sign_up

import androidx.compose.runtime.Composable
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.class_sign_up.components.ClassSignUpContent

@Composable
fun ClassSignUpScreen(
    onBack: () -> Unit
) {
    StatusBarStyle(darkIcons = true)

    ClassSignUpContent(
        onBack = onBack
    )
}