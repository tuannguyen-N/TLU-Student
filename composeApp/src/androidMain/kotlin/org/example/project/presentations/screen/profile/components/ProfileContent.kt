package org.example.project.presentations.screen.profile.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.me.StudentData
import org.example.project.presentations.theme.LocalExtendedColors

@SuppressLint("FrequentlyChangingValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    student: StudentData?,
    onOpenEditProfile: () -> Unit = {},
    onOpenSetting: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val listState = rememberLazyListState()
    val scrollOffset = listState.firstVisibleItemScrollOffset
    val maxHeight = 160.dp
    val minHeight = 56.dp
    val maxHeightPx = with(LocalDensity.current) { maxHeight.toPx() }
    val minHeightPx = with(LocalDensity.current) { minHeight.toPx() }
    val collapseRange = maxHeightPx - minHeightPx
    val progress = (scrollOffset / collapseRange).coerceIn(0f, 1f)

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HeaderProfile(
                progress = progress,
                studentName = student?.fullName.orEmpty(),
                majorName = student?.major?.majorName.orEmpty(),
                onClickSetting = onOpenSetting,
                onClickBack = onBack
            )
        }
    ) { paddingValues ->

        LazyColumn(
            state = listState,
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
                    birthDay = student?.dateOfBirth.orEmpty(),
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
                    educationMode = "Chính Quy" /*student?.academicInfo?.educationMode.orEmpty()*/
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