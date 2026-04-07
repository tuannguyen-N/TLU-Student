package org.example.project.data.remote.dto.notification_prepare

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val courseClassIds: List<Int>,
    val facultyId: Int,
    val oauthUserId: Int,
    val studentClassId: Int,
    val topics: List<String>
)