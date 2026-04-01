package org.example.project.presentations.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.profile.components.ProfileContent

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onOpenSetting: () -> Unit = {},
    onOpenEditProfile: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val student = uiState.studentInfo

    StatusBarStyle(darkIcons = false)

    ProfileContent(
        student = student,
        onOpenEditProfile = onOpenEditProfile,
        onOpenSetting = onOpenSetting,
        onBack = onBack
    )
}