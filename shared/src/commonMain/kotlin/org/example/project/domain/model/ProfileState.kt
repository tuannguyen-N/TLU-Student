package org.example.project.domain.model

import org.example.project.data.remote.dto.me.StudentInformation

data class ProfileState(
    val studentInfo: StudentInformation? = null
)