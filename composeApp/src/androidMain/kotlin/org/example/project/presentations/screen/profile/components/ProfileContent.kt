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
import org.example.project.data.remote.dto.me.AcademicInfo
import org.example.project.data.remote.dto.me.Contact
import org.example.project.data.remote.dto.me.EmergencyContact
import org.example.project.data.remote.dto.me.IdentityCard
import org.example.project.data.remote.dto.me.Major
import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.presentations.theme.LocalExtendedColors

@SuppressLint("FrequentlyChangingValue")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileContent(
    student: StudentInformation?,
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

@Preview(showBackground = true)
@Composable
fun ProfileContentPreview() {
    val fakeStudent = StudentInformation(
        academicAdvisor = "Nguyễn Văn A",
        academicInfo = AcademicInfo(
            position = "Sinh viên",
            cohort = "K2021",
            educationMode = "Chính quy"
        ),
        classCode = "D21CNTT01",
        contact = Contact(
            phoneNumber = "0123456789",
            email = "ducpm@example.com",
            address = "Hà Nội"
        ),
        dateOfBirth = "2003-05-20",
        emergencyContact = EmergencyContact(
            name = "Phạm Văn B",
            phoneNumber = "0987654321",
            address = "Nam Định"
        ),
        fullName = "Phạm Minh Đức",
        gender = "Nam",
        identityCard = IdentityCard(
            cardNumber = "012345678901",
            cardType = "123",
            issuedDate = "123",
            issuedPlace = "Hà Nội"
        ),
        major = Major(
            majorName = "Công nghệ thông tin",
            majorCode = "D21CNTT01",
            faculty = "Khoa CNTT"
        ),
        studentCode = "SV2021001"
    )
    ProfileContent(
        student = fakeStudent
    )
}