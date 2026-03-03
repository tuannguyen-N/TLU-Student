package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.TimetableSubject

@Preview(showBackground = true)
@Composable
fun TimetableGrid(
    subjects: List<TimetableSubject> = TimetableSubject.getDataDemo(),
    totalDays: Int = 6,
    totalPeriods: Int = 15,
    onShowSubjectDetail: () -> Unit = {}
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
            it.measure(
                Constraints.fixed(totalWidth, totalHeight)
            )
        }

        val subjectPlaceables = subjects.map { subject ->
            subcompose(subject.id) {
                SubjectInformationCard(
                    modifier = Modifier.fillMaxSize(),
                    onShowSubjectDetail = onShowSubjectDetail
                )
            }.first().measure(
                Constraints.fixed(
                    width = cellWidth,
                    height = cellHeight * subject.duration
                )
            )
        }

        layout(totalWidth, totalHeight) {

            gridPlaceables.forEach {
                it.place(0, 0)
            }

            subjects.forEachIndexed { index, subject ->

                val x = leftColumnWidth + subject.day * cellWidth
                val y = headerHeight + subject.startPeriod * cellHeight

                subjectPlaceables[index].placeWithLayer(x, y)
            }
        }
    }
}