package org.example.project.presentations.screen.exam_schedule.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.mapper.today
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun SemesterSelector(
    modifier: Modifier = Modifier,
    semesters: List<Semester>,
    selectedSemester: Semester?,
    onSemesterSelected: (Semester) -> Unit = {},
    onToggleDropdown: () -> Unit = {},
    isDropdownExpanded: Boolean = false
) {
    Column(modifier = modifier) {
        Text(
            text = "Chọn Học Kỳ",
            style = MaterialTheme.typography.labelLarge.copy(
                color = LocalExtendedColors.current.gray,
                letterSpacing = 0.5.sp
            )
        )

        Spacer(Modifier.height(8.dp))

        Box {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = BorderStroke(1.dp, LocalExtendedColors.current.lightGray)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onToggleDropdown() }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedSemester?.semesterName ?: "$today",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                        )
                    )
                    Icon(
                        imageVector = if (isDropdownExpanded)
                            Icons.Default.KeyboardArrowUp
                        else
                            Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = LocalExtendedColors.current.gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            DropdownMenu(
                expanded = isDropdownExpanded,
                onDismissRequest = onToggleDropdown,
                modifier = Modifier
                    .background(Color.White),
            ) {
                semesters.forEachIndexed { index, semester ->
                    val isSelected = semester.semesterName == selectedSemester?.semesterName

                    DropdownMenuItem(
                        text = {
                            Text(
                                text = semester.semesterName,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.SemiBold
                                    else FontWeight.Normal,
                                    color = if (isSelected)
                                        LocalExtendedColors.current.fontBlue
                                    else
                                        Color(0xFF1A1A2E)
                                ),
                            )
                        },
                        trailingIcon = {
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = LocalExtendedColors.current.fontBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        onClick = {
                            onSemesterSelected(semester)
                            onToggleDropdown()
                        },
                        modifier = Modifier.background(
                            if (isSelected) Color(0xFFF0F6FF) else Color.White
                        )
                    )

                    if (index < semesters.lastIndex) {
                        HorizontalDivider(
                            color = Color(0xFFF3F4F6),
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }
            }
        }
    }
}