package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.DailySchedule

@Composable
fun TimetableGrid(
    dailySchedules: List<DailySchedule>,
    totalDays: Int = 6,
    totalPeriods: Int = 15,
    onShowSubjectDetail: (CourseClass) -> Unit = {}
) {
    SubcomposeLayout {

        val cellWidth = 100.dp.roundToPx()
        val cellHeight = 60.dp.roundToPx()
        val leftColumnWidth = 50.dp.roundToPx()

        val totalWidth = leftColumnWidth + cellWidth * totalDays
        val headerHeight = cellHeight / 2
        val totalHeight = headerHeight + cellHeight * totalPeriods

        val gridPlaceables = subcompose("grid") {
            GridBackground(
                totalDays = totalDays,
                totalPeriods = totalPeriods,
                cellWidth = 100.dp,
                cellHeight = 60.dp,
                leftColumnWidth = 50.dp
            )
        }.map {
            it.measure(Constraints.fixed(totalWidth, totalHeight))
        }

        val subjectPlaceables = dailySchedules.map { daily ->
            daily.courseClasses.map { courseClass ->
                val endPeriod = courseClass.endPeriod
                val startPeriod = courseClass.startPeriod

                subcompose("${courseClass.subjectCode}_${courseClass.dayOfWeek}_${courseClass.startPeriod}") {
                    SubjectInformationCard(
                        modifier = Modifier.fillMaxSize(),
                        room = courseClass.room,
                        subjectName = courseClass.subjectName,
                        onShowSubjectDetail = { onShowSubjectDetail(courseClass) }
                    )
                }.first().measure(
                    Constraints.fixed(
                        width = cellWidth,
                        height = cellHeight * (endPeriod - startPeriod)
                    )
                )
            }
        }

        layout(totalWidth, totalHeight) {
            gridPlaceables.forEach {
                it.place(0, 0)
            }

            dailySchedules.forEachIndexed { dayIndex, daily ->
                daily.courseClasses.forEachIndexed { classIndex, subject ->
                    val dayOfWeek = subject.dayOfWeek
                    val startPeriod = subject.startPeriod

                    val x = leftColumnWidth + (dayOfWeek - 1) * cellWidth
                    val y = headerHeight + startPeriod * cellHeight

                    subjectPlaceables[dayIndex][classIndex].placeWithLayer(x, y)
                }
            }
        }
    }
}