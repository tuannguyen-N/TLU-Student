package org.example.project.presentations.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.example.project.presentations.screen.profile.components.ContactPersonInformation
import org.example.project.presentations.screen.profile.components.HeaderProfile
import org.example.project.presentations.screen.profile.components.StudentInformation
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun ProfileScreen(
    rootNavController: NavController = rememberNavController(),
    onOpenSetting: () -> Unit = {},
    onOpenEditProfile: () -> Unit = {}
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HeaderProfile(
                onClickBack = { rootNavController.popBackStack() },
                onClickSetting = onOpenSetting
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {

            item {
                StudentInformation(
                    onEditProfile = onOpenEditProfile,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }

            item {
                ContactPersonInformation(
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .padding(bottom = 50.dp)
                )
            }
        }
    }
}