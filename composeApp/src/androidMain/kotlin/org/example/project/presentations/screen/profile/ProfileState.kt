package org.example.project.presentations.screen.profile

import org.example.project.data.remote.dto.me.StudentData

data class ProfileState(
    val studentInfo: StudentData? = null,
    val avatarBase64: String? = null
)