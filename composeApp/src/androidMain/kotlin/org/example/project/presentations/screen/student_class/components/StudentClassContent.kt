package org.example.project.presentations.screen.student_class.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.student_class.StudentClassState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun StudentClassContent(
    uiState: StudentClassState,
    onBack: () -> Unit,
    onCopy: (String, String) -> Unit,
    onMessageClick: (String, String, String?) -> Unit,
) {
    val classInfoData = uiState.studentClassInfoData
    val color = LocalExtendedColors.current

    val roleMap = mapOf(
        "20240001" to StudentRole.CLASS_PRESIDENT,
        "20240002" to StudentRole.CLASS_VICE,
        "20240003" to StudentRole.SECRETARY,
    )

    var searchQuery by remember { mutableStateOf("") }
    val students = classInfoData?.students ?: emptyList()
    val filtered = remember(searchQuery, students) {
        if (searchQuery.isBlank()) students
        else students.filter {
            it.fullName.contains(searchQuery, ignoreCase = true) ||
                    it.studentCode.contains(searchQuery, ignoreCase = true)
        }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        containerColor = color.background,
        topBar = {
            TopCenterScreenBar(
                title = "Lớp hành chính sinh viên",
                enableActionButton = false,
                backgroundColor = color.white,
                contentColor = color.blackBackground,
                onBack = onBack,
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item(key = "class_card") {
                ClassCard(
                    color = color,
                    className = classInfoData?.classCode ?: "_",
                    major = classInfoData?.majorName ?: "_",
                )
            }

            item(key = "teacher_card") {
                Spacer(Modifier.height(20.dp))
                TeacherCard(
                    teacherName = classInfoData?.academicAdvisor?.fullName ?: "_",
                    teacherId = classInfoData?.academicAdvisor?.lecturerCode ?: "_",
                    email = classInfoData?.academicAdvisor?.email ?: "_",
                    phone = classInfoData?.academicAdvisor?.phoneNumber ?: "_",
                    color = color,
                    onCopy = {
                        onCopy(it, "")
                    }
                )
            }

            studentListItems(
                students = students,
                filtered = filtered,
                roleMap = roleMap,
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                color = color,
                onMessageClick = onMessageClick
            )

            item(key = "bottom_spacer") {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}