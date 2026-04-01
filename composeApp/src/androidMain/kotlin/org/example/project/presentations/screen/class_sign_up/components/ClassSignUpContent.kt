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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.CourseFilter
import org.example.project.domain.model.CourseItem
import org.example.project.domain.model.CourseStatus
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassSignUpContent(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    val allCourses = remember {
        listOf(
            CourseItem(
                "IT101",
                3,
                "Lập trình hướng đối tượng",
                CourseStatus.AVAILABLE,
                CourseFilter.REQUIRED
            ),
            CourseItem(
                "IT101",
                3,
                "Lập trình hướng đối tượng",
                CourseStatus.AVAILABLE,
                CourseFilter.REQUIRED
            ),
            CourseItem("MA303", 3, "Xác suất thống kê", CourseStatus.FULL, CourseFilter.REQUIRED),
            CourseItem(
                "IT205",
                3,
                "Cấu trúc dữ liệu & Giải thuật",
                CourseStatus.AVAILABLE,
                CourseFilter.REQUIRED
            ),
            CourseItem("EN101", 2, "Tiếng Anh 1", CourseStatus.AVAILABLE, CourseFilter.ELECTIVE),
            CourseItem("PE201", 1, "Giáo dục thể chất", CourseStatus.FULL, CourseFilter.ELECTIVE),
        )
    }

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
            TopScreenBar<String>(
                title = "Đăng ký học",
                onBack = onBack,
                enableListItem = true,
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
                    SemesterInformation(color)
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
                    selectedFilter = courseFilter(selectedFilter, color)
                }

                items(filtered) { course ->
                    CourseCard(
                        course = course,
                        color = color,
                        onSignUp = {
                            // TODO:
                        }
                    )
                }

                item { Spacer(Modifier.height(60.dp)) }
            }

            ClassSelectedInformationCard(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun courseFilter(
    selectedFilter: CourseFilter,
    color: ExtendedColors
): CourseFilter {
    var selectedFilter1 = selectedFilter
    val chips = listOf(
        "Tất cả" to CourseFilter.ALL,
        "Bắt buộc" to CourseFilter.REQUIRED,
        "Tự do" to CourseFilter.ELECTIVE
    )
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(chips) { (label, filter) ->
            val selected = selectedFilter1 == filter
            FilterChip(
                selected = selected,
                onClick = { selectedFilter1 = filter },
                label = { Text(label, style = MaterialTheme.typography.labelMedium) },
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
    return selectedFilter1
}