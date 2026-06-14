package org.example.project.domain.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.local.dao.SemesterDao
import org.example.project.data.mapper.toMarkedEntity
import org.example.project.data.mapper.toSemester
import org.example.project.data.remote.api.SemesterApi
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.semester_preiod.SemesterPeriodData
import org.example.project.domain.model.AppResult

class SemesterRepository(
    private val semesterApi: SemesterApi,
    private val semesterDao: SemesterDao,
    private val studyProgramApi: StudyProgramApi
) {
    private val _semesters = MutableStateFlow<List<Semester>?>(null)
    val semesters = _semesters.asStateFlow()

    suspend fun getSemesters(): AppResult<List<Semester>> {
        return try {
            val data = semesterApi.getSemesters().data
                ?: return AppResult.Failure(message = "Không có dữ liệu học kỳ")

            semesterDao.insertSemesters(data.map { it.toMarkedEntity() })

            _semesters.value = data
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getSemestersOffline(): AppResult<List<Semester>> {
        return try {
            val data = semesterDao.getAllSemesters().map { it.toSemester() }
            if (data.isEmpty()) {
                AppResult.Failure(message = "Không có dữ liệu học kỳ offline")
            } else {
                AppResult.Success(data)
            }
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }

    suspend fun getSemesterPeriod(): AppResult<SemesterPeriodData> {
        return try {
            val studyProgram = studyProgramApi.getStudyPrograms().data?.firstOrNull()
                ?: return AppResult.Failure(message = "Không có dữ liệu học kỳ")
            val data = semesterApi.getSemesterPeriod(studyProgram.studyProgramCode).data
                ?: return AppResult.Failure(message = "Không có dữ liệu học kỳ")
            AppResult.Success(data)
        } catch (e: Exception) {
            AppResult.Failure(message = e.message, cause = e)
        }
    }
}