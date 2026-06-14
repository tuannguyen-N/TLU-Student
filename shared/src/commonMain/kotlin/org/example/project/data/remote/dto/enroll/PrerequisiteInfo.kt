package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class PrerequisiteInfo(
    val groupId: Int,
    val needMore: Int,
    val missingSubjectCodes: List<String>
)