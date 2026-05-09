package org.example.project.presentations.screen.gpa_predict

import org.example.project.domain.model.SubjectItem
import org.example.project.domain.model.SubjectScore

data class GpaPredictState(
    val subjects: List<SubjectItem> = emptyList(),
    val scores: Map<String, SubjectScore> = emptyMap(),
    val failedSubjects: List<String> = emptyList(),
    val realGpa: Double = 0.0,
    val predictedGpa: Double? = null,
    val passedPredictedCredit: Int? = null,
    val passedRealCredit: Int = 0,
    val totalCredit: Int = 0,

    val isLoading: Boolean = false
)
