package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassEnrollmentData
import org.example.project.domain.model.CourseFilter
import org.example.project.domain.model.CourseItem
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.class_sign_up.ClassSignUpState
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassSignUpContent(
    modifier: Modifier = Modifier,
    uiState: ClassSignUpState,
    onBack: () -> Unit,
    onSelectedSchedule: (CourseItem) -> Unit,
    onDismissSelectedScheduleDialog: () -> Unit,
    onEnrollClass: (CourseClassEnrollmentData) -> Unit,
    onOpenSignedUpClass: () -> Unit
) {
    val allCourses = uiState.courses

    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(CourseFilter.ALL) }

    val filtered = allCourses.filter { course ->
        val matchFilter = selectedFilter == CourseFilter.ALL || course.category == selectedFilter
        val matchSearch = searchQuery.isBlank() ||
                course.name.contains(searchQuery, ignoreCase = true) ||
                course.code.contains(searchQuery, ignoreCase = true)
        matchFilter && matchSearch
    }
    val color = LocalExtendedColors.current
    Scaffold(
        modifier = modifier,
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                title = "Đăng ký học",
                onBack = onBack,
                backgroundColor = LocalExtendedColors.current.white,
                contentColor = Color.Black
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    SemesterInformation(
                        color,
                        semesterName = uiState.semesterName,
                        enrollmentStartTime = uiState.enrollmentStartTime,
                        enrollmentEndTime = uiState.enrollmentEndTime,
                    )
                }

                item {
                    CourseNameInputField(
                        searchQuery = searchQuery,
                        hint = "Tìm kiếm môn học...",
                        onSearchQueryChange = { searchQuery = it },
                        color = LocalExtendedColors.current
                    )
                }

                item {
                    CourseFilterRow(
                        selectedFilter = selectedFilter,
                        color = color,
                        onFilterSelected = {
                            selectedFilter = it
                        }
                    )
                }

                items(filtered) { course ->
                    CourseCard(
                        course = course,
                        color = color,
                        onSignUp = {
                            onSelectedSchedule(it)
                        }
                    )
                }

                item { Spacer(Modifier.height(90.dp)) }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (uiState.error != null) {
                Text(
                    text = uiState.error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (allCourses.isEmpty()) {
                Text(
                    text = "Chưa đến thời gian đăng ký",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                ClassSelectedInformationCard(
                    totalSubjects = uiState.totalSubjects,
                    totalCredits = uiState.totalCredits,
                    onConfirmClick = onOpenSignedUpClass,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                )
            }

            if (uiState.showDialog) {
                SchedulePickerDialog(
                    courseTitle = uiState.selectedCourseTitle,
                    onDismiss = onDismissSelectedScheduleDialog,
                    isLoading = uiState.isDialogLoading,
                    classGroups = uiState.courseClasses,
                    enrolledClassCodes = uiState.enrolledClasses.map { it.classCode }.toSet(),
                    onSelect = onEnrollClass
                )
            }

            if (uiState.isEnrolling) {
                EnrollLoadingDialog()
            }
        }
    }
}

@Composable
private fun CourseFilterRow(
    selectedFilter: CourseFilter,
    color: ExtendedColors,
    onFilterSelected: (CourseFilter) -> Unit
) {
    val chips = listOf(
        "Tất cả" to CourseFilter.ALL,
        "Bắt buộc" to CourseFilter.REQUIRED,
        "Tự do" to CourseFilter.ELECTIVE
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(chips) { (label, filter) ->

            val selected = selectedFilter == filter

            FilterChip(
                selected = selected,

                onClick = {
                    onFilterSelected(filter)
                },

                label = {
                    Text(
                        label,
                        style = MaterialTheme.typography.labelMedium
                    )
                },

                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = color.fontBlue,
                    selectedLabelColor = Color.White,
                    containerColor = LocalExtendedColors.current.white,
                    labelColor = Color.Black
                ),

                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = selected,
                    borderColor = Color(0xFFCCCCCC),
                    borderWidth = 1.dp,
                    selectedBorderWidth = 1.dp
                )
            )
        }
    }
}