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
    viewModel: ProfileViewModel,
    onOpenSetting: () -> Unit = {},
    onOpenEditProfile: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val student = uiState.studentInfo

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HeaderProfile(
                onClickBack = onBack,
                onClickSetting = onOpenSetting,
                studentName = student?.fullName.orEmpty(),
                majorName = student?.major?.majorName.orEmpty()
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
                    studentCode = student?.studentCode.orEmpty(),
                    fullName = student?.fullName.orEmpty(),
                    gender = student?.gender.orEmpty(),
                    cardNumber = student?.identityCard?.cardNumber.orEmpty(),
                    phoneNumber = student?.contact?.phoneNumber.orEmpty(),
                    email = student?.contact?.email.orEmpty(),
                    address = student?.contact?.address.orEmpty(),
                    onEditProfile = onOpenEditProfile,
                    modifier = Modifier.padding(horizontal = 25.dp)
                )
            }

            item {
                AcademicInformation(
                    modifier = Modifier.padding(horizontal = 25.dp),
                    classCode = student?.classCode.orEmpty(),
                    position = student?.academicInfo?.position.orEmpty(),
                    academicAdvisor = student?.academicAdvisor.orEmpty(),
                    cohort = student?.academicInfo?.cohort.orEmpty(),
                    educationMode = student?.academicInfo?.educationMode.orEmpty()
                )
            }

            item {
                ContactPersonInformation(
                    contactName = student?.emergencyContact?.name.orEmpty(),
                    contactPhone = student?.emergencyContact?.phoneNumber.orEmpty(),
                    contactAddress = student?.emergencyContact?.address.orEmpty(),
                    modifier = Modifier
                        .padding(horizontal = 25.dp)
                        .padding(bottom = 50.dp)
                )
            }
        }
    }
}