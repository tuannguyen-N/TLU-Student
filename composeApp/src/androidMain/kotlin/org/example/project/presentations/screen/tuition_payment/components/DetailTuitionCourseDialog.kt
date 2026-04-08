package org.example.project.presentations.screen.tuition_payment.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.data.mapper.toFormatAmountAndD
import org.example.project.data.remote.dto.tuition_detail.TuitionItem
import org.example.project.presentations.components.DashedDivider
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun DetailTuitionCourseDialog(
    courses: List<TuitionItem>,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Column(
            modifier = modifier
                .background(color = Color.White, shape = RoundedCornerShape(18.dp))
                .fillMaxWidth()
                .wrapContentHeight()
                .heightIn(max = 400.dp)
                .padding(top = 18.dp)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chi tiết môn học",
                    style = MaterialTheme.typography.titleLarge,
                    color = LocalExtendedColors.current.mainBlue,
                    fontWeight = FontWeight.SemiBold
                )

                IconButton(
                    onClick = onDismiss,
                    modifier = Modifier.size(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            DashedDivider(
                color = LocalExtendedColors.current.blackBackground,
                strokeWidth = 0.5.dp,
                dashLength = 5.dp,
                gapLength = 5.dp
            )

            LazyColumn {
                items(courses) { course ->
                    TuitionCourseItem(
                        courseCode = "course.code", // TODO:
                        courseName = course.subjectName,
                        credits = course.credits,
                        amount = course.amount.toLong().toFormatAmountAndD(),
                        color = LocalExtendedColors.current,
                        isLastItem = courses.last() == course
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDetailTuitionCourseDialog() {
    val mockCourses = listOf(
        TuitionItem(
            id = 1,
            subjectName = "Lập trình Android",
            credits = 3,
            coefficient = 1.0,
            pricePerCredit = 500000.0,
            amount = 1500000.0,
            retake = false
        ),
        TuitionItem(
            id = 2,
            subjectName = "Cấu trúc dữ liệu",
            credits = 4,
            coefficient = 1.2,
            pricePerCredit = 500000.0,
            amount = 2400000.0,
            retake = true
        )
    )

    DetailTuitionCourseDialog(
        courses = mockCourses,
        onDismiss = {}
    )
}