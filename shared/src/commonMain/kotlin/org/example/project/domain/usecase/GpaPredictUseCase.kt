package org.example.project.domain.usecase

import org.example.project.domain.model.SubjectScore

class GpaPredictUseCase {
    fun getFailedSubject(scores: Map<String, SubjectScore>): List<String> {
        return scores.filter { it.value.grade < 4.0 }.keys.toList()
    }

    fun predictGpa(scores: Map<String, SubjectScore>, realGpa: Double, realCredit: Int): Pair<Double, Int> {
        val validScores = scores.values.filter { it.grade >= 4.0 }
        val totalInputScore = validScores.sumOf { it.grade * it.credit } * 0.4
        val totalInputCredit = validScores.sumOf { it.credit }
        val totalRealScore = realGpa * realCredit
        return Pair(
            (totalInputScore + totalRealScore) / (totalInputCredit + realCredit),
            totalInputCredit + realCredit
        )
    }
}