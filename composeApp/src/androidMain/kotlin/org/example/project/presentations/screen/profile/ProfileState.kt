package org.example.project.presentations.screen.profile

import org.example.project.data.remote.dto.me.StudentInformation

data class ProfileState(
    val studentInfo: StudentInformation? = null
)