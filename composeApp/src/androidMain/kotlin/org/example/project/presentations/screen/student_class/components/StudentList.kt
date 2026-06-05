package org.example.project.presentations.screen.student_class.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.student_class.Student
import org.example.project.presentations.components.LabelHeader
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.screen.class_sign_up.components.CourseNameInputField
import org.example.project.presentations.theme.ExtendedColors

fun LazyListScope.studentListItems(
    students: List<Student>,
    filtered: List<Student>,
    roleMap: Map<String, StudentRole>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    color: ExtendedColors,
    onMessageClick: (String, String) -> Unit,
) {
    item(key = "student_list_header") {
        Spacer(modifier = Modifier.height(20.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                LabelHeader(label = "Danh sách sinh viên")
                LabelView(
                    text = "${students.size} thành viên",
                    textColor = color.midBlue,
                    backgroundColor = color.midBlue.copy(alpha = 0.1f),
                )
            }

            CourseNameInputField(
                searchQuery = searchQuery,
                hint = "Tìm kiếm theo MSSV hoặc tên...",
                onSearchQueryChange = onSearchQueryChange,
                color = color,
            )
        }
    }

    if (filtered.isEmpty()) {
        item(key = "student_list_empty") {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Không tìm thấy sinh viên nào",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    ),
                )
            }
        }
        return
    }

    itemsIndexed(
        items = filtered,
        key = { _, student -> student.studentCode },
    ) { _, student ->
        Spacer(Modifier.height(10.dp))
        StudentItem(
            student = student,
            role = roleMap[student.studentCode] ?: StudentRole.NONE,
            color = color,
            onMessageClick = onMessageClick,
        )
    }
}
