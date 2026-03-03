package org.example.project.domain.model

data class TimetableSubject(
    val id: String,
    val day: Int,
    val startPeriod: Int,
    val duration: Int
) {
    companion object {
        fun getDataDemo(): List<TimetableSubject> {
            return listOf(
                TimetableSubject("1", day = 0, startPeriod = 0, duration = 1),
                TimetableSubject("2", day = 0, startPeriod = 3, duration = 2),
                TimetableSubject("3", day = 2, startPeriod = 0, duration = 1),
            )
        }
    }
}