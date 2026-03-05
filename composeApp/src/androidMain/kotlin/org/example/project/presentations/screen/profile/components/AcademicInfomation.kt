package org.example.project.presentations.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun AcademicInformation(
    modifier: Modifier = Modifier,
    classCode: String,
    position: String,
    academicAdvisor: String,
    cohort: String,
    educationMode: String
) {
    Column(modifier = modifier) {

        Text(
            text = "Thông tin học tập",
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(13.dp))
                .background(LocalExtendedColors.current.white)
                .padding(horizontal = 15.dp)
        ) {

            InformationItem(
                R.drawable.icon_student_class,
                "Lớp sinh viên",
                classCode
            )

            InformationItem(
                R.drawable.icon_class_position,
                "Chức vụ",
                position
            )

            AcademicAdvisorItem(
                R.drawable.icon_academic_advisor,
                "Cố vấn học tập",
                "Nguyễn Var Tày",
                academicAdvisor
            )

            InformationItem(
                R.drawable.icon_course,
                "Khoá học",
                cohort
            )

            InformationItem(
                R.drawable.icon_course,
                "Hệ",
                educationMode
            )
        }
    }
}