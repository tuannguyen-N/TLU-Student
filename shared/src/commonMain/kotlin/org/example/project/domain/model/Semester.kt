package org.example.project.domain.model

data class Semester( // TODO: delete when complete
    val id: String,
    val label: String
) {
    companion object {
        fun getSampleSemesters(): List<Semester> = listOf(
            Semester("HK1_2526", "Học kỳ 1 - 2025/2026"),
            Semester("HK2_2526", "Học kỳ 2 - 2025/2026"),
            Semester("HK1_2425", "Học kỳ 1 - 2024/2025"),
            Semester("HK2_2425", "Học kỳ 2 - 2024/2025"),
        )
    }
}