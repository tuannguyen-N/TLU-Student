package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.computeEnrollmentStatusText
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun SemesterInformation(
    color: ExtendedColors,
    semesterName: String,
    enrollmentStartTime: String? = null,
    enrollmentEndTime: String? = null,
    hasSubjects: Boolean = true,
) {
    val enrollmentStatusText = remember(enrollmentStartTime, enrollmentEndTime, hasSubjects) {
        computeEnrollmentStatusText(enrollmentStartTime, enrollmentEndTime, hasSubjects)
    }

    Spacer(Modifier.height(16.dp))
    Text(
        text = semesterName,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onBackground
    )
    if (enrollmentStatusText != null) {
        Spacer(Modifier.height(4.dp))
        Text(
            text = enrollmentStatusText,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Normal,
            color = color.gray
        )
    }
}