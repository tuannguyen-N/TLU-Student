package org.example.project.domain.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.data.remote.dto.student_search.StudentSummary
import org.example.project.domain.model.User

interface UserRepository {
    suspend fun getUsers(
        size: Int,
        excludeUserId: String? = null
    ): List<User>

    suspend fun uploadUsers(
        students: List<StudentSummary>
    )

    suspend fun uploadUser(
        student: StudentSummary
    )

    suspend fun removeToken(userId: String)

    fun observeUsers(
        size: Int,
        excludeUserId: String?
    ): Flow<List<User>>
}