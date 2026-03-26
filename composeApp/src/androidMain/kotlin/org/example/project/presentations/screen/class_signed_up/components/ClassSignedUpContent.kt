package org.example.project.presentations.screen.class_signed_up.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.data.remote.dto.day_schedule.CourseClasses
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.Lecturer
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.class_signed_up.ClassSignedUpState
import org.example.project.presentations.screen.transcript_term.components.SubjectCode
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ClassSignedUpContent(
    modifier: Modifier = Modifier,
    uiState: ClassSignedUpState,
    color: ExtendedColors = LocalExtendedColors.current,
    onBack: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                title = "Môn học đã đăng ký",
                onBack = onBack,
                backgroundColor = color.white,
                contentColor = Color.Black
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .heightIn(min = 200.dp)
        ) {
            Spacer(Modifier.height(5.dp))

            if (uiState.courseClasses != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(Modifier.height(18.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 160.dp)
                    ) {
                        items(
                            items = uiState.courseClasses.courseClasses,
                            key = { it.classCode }
                        ) { courseClass ->
                            CourseItem(
                                isRequired = false, // TODO:
                                color = color,
                                courseClass = courseClass,
                                onDeleteClick = {
                                    // TODO: onClickDelete
                                }
                            )
                        }
                    }
                }
            } else {
                EmptyClassSignUp(
                    color = color,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 35.dp)
                )
            }

            ConfirmSignUp(
                modifier = Modifier.align(Alignment.BottomCenter),
                onConfirm = onConfirm
            )
        }
    }
}

@Preview
@Composable
fun PreviewClassSignedUpContent() {
    val sampleCourse = CourseClass(
        classCode = "INT2204",
        dayOfWeek = 2,
        endPeriod = 5,
        endTime = "10:30:00",
        room = "A101",
        startPeriod = 3,
        startTime = "08:45:00",
        subjectCode = "INT2204",
        subjectName = "Lập trình Android",
        lecturer = Lecturer(
            fullName = "Nguyễn Văn A",
            lecturerCode = "123",
            phoneNumber = "",
            email = "vana@example.com"
        )
    )


    ClassSignedUpContent(
        uiState = ClassSignedUpState(CourseClasses(listOf(sampleCourse)))
    )
}


@Composable
private fun ConfirmSignUp(
    modifier: Modifier = Modifier,
    color: ExtendedColors = LocalExtendedColors.current,
    onConfirm: () -> Unit
) {
    Column(
        modifier = modifier.background(color.white)
    ) {
        HorizontalDivider(thickness = 0.5.dp, color = color.gray)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column {
                Text(
                    text = "Tổng số tín chỉ",
                    style = MaterialTheme.typography.labelLarge,
                    color = color.gray,
                    fontWeight = FontWeight.Normal
                )

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "10",
                        style = MaterialTheme.typography.headlineSmall,
                        color = color.mainBlue,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "Tín",
                        style = MaterialTheme.typography.labelSmall,
                        color = color.gray,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 5.dp, bottom = 4.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Tổng số môn",
                    style = MaterialTheme.typography.labelLarge,
                    color = color.gray,
                    fontWeight = FontWeight.Normal
                )

                Text(
                    text = "10",
                    style = MaterialTheme.typography.headlineSmall,
                    color = color.mainBlue,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ButtonView(
            enabled = true,
            text = "Xác nhận đăng ký",
            modifier = Modifier
                .height(60.dp)
                .padding(horizontal = 25.dp),
            shape = RoundedCornerShape(24.dp),
            backgroundColorRes = color.mainBlue,
            textColorRes = color.white,
            onClick = onConfirm
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
private fun CourseItem(
    isRequired: Boolean,
    courseClass: CourseClass,
    color: ExtendedColors,
    onDeleteClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = color.white,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(16.dp)) {

                IconButton(
                    onClick = onDeleteClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f))
                        .size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_delete),
                        contentDescription = "Xóa môn học",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 40.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        SubjectCode(
                            name = courseClass.subjectCode,
                            color = color.fontBlue
                        )

                        if (isRequired) {
                            Text(
                                text = "BẮT BUỘC",
                                style = MaterialTheme.typography.labelSmall,
                                color = color.grayNavy,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            append(courseClass.subjectName)
                            append(" ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 15.sp),) {
                                append("(${courseClass.classCode})")
                            }
                        },
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = null,
                            tint = color.gray,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = courseClass.lecturer.fullName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = color.gray
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = null,
                                tint = color.gray,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "", // TODO:
                                style = MaterialTheme.typography.bodyMedium,
                                color = color.gray
                            )
                        }


                    }
                }

                Text(
                    text = "1 Tín chỉ", // TODO: add credit
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.BottomEnd)
                )
            }
        }
    }
}