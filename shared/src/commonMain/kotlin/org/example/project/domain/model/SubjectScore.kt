package org.example.project.domain.model

data class SubjectScore(
    val midterm: String = "",
    val final: String = "",
    val credit: Int = 3
) {
    val midtermDouble get() = midterm.toDoubleOrNull() ?: 0.0
    val finalDouble get() = final.toDoubleOrNull() ?: 0.0
    val grade get() = (midtermDouble * 0.3 + finalDouble * 0.7 )
}