package org.example.project.data.remote.dto.tuition_detail

import kotlinx.serialization.Serializable

@Serializable
data class TuitionItem(
    val amount: Double,
    val coefficient: Double,
    val subjectCode: String,
    val credits: Int,
    val id: Int,
    val retake: Boolean,
    val pricePerCredit: Double,
    val subjectName: String
)