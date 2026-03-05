package org.example.project.presentations.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.profile.components.AcademicInformation
import org.example.project.presentations.screen.profile.components.ContactPersonInformation
import org.example.project.presentations.screen.profile.components.HeaderProfile
import org.example.project.presentations.screen.profile.components.PersonalInformation
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onOpenSetting: () -> Unit = {},
    onOpenEditProfile: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val uiState by profileViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HeaderProfile(
                onClickBack = onBack,
                onClickSetting = onOpenSetting,
                studentName = uiState.studentInfo?.fullName ?: "",
                majorName = uiState.studentInfo?.major?.majorName ?: ""
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
                PersonalInformation(
                    studentCode = uiState.studentInfo?.studentCode ?: "",
                    fullName = uiState.studentInfo?.fullName ?: "",
                    gender = uiState.studentInfo?.gender ?: "",
                    cardNumber = uiState.studentInfo?.identityCard?.cardNumber ?: "",
                    phoneNumber = uiState.studentInfo?.contact?.phoneNumber ?: "",
                    email = uiState.studentInfo?.contact?.email ?: "",
                    address = uiState.studentInfo?.contact?.address ?: "",
                    onEditProfile = onOpenEditProfile,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }

            item {
                AcademicInformation(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    classCode = uiState.studentInfo?.classCode ?: "",
                    position = uiState.studentInfo?.academicInfo?.position ?: "",
                    academicAdvisor = uiState.studentInfo?.academicAdvisor ?: "",
                    cohort = uiState.studentInfo?.academicInfo?.cohort ?: "",
                    educationMode = uiState.studentInfo?.academicInfo?.educationMode ?: ""
                )
            }

            item {
                ContactPersonInformation(
                    contactName = uiState.studentInfo?.emergencyContact?.name ?: "",
                    contactPhone = uiState.studentInfo?.emergencyContact?.phoneNumber ?: "",
                    contactAddress = uiState.studentInfo?.emergencyContact?.address ?: "",
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .padding(bottom = 50.dp)
                )
            }
        }
    }
}