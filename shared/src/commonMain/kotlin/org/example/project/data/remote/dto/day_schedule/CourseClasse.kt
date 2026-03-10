package org.example.project.data.remote.dto.schedule

import kotlinx.serialization.Serializable

@Serializable
data class CourseClass(
    val classCode: String,
    val dayOfWeek: Int,
    val endPeriod: Int,
    val endTime: String,
    val lecturerEmail: String,
    val lecturerName: String,
    val room: String,
    val startPeriod: Int,
    val startTime: String,
    val subjectCode: String,
    val subjectName: String
)

//){
//    companion object {
//        fun getDemoData(): List<CourseClass> {
//            return listOf(
//                CourseClass(
//                    classCode = "LTH001",
//                    dayOfWeek = 1,
//                    endPeriod = 1,
//                    endTime = "12:00",
//                    lecturerEmail = "william.henry.harrison@example-pet-store.vn",
//                    lecturerName = "Nguyễn Văn A",
//                    room = "A101",
//                    startPeriod = 1,
//                    startTime = "07:30",
//                    subjectCode = "LTH001",
//                    subjectName = "Lập trình hướng đối tượng"
//                ),CourseClass(
//                    classCode = "LTH001",
//                    dayOfWeek = 1,
//                    endPeriod = 1,
//                    endTime = "12:00",
//                    lecturerEmail = "william.henry.harrison@example-pet-store.vn",
//                    lecturerName = "Nguyễn Văn A",
//                    room = "A101",
//                    startPeriod = 1,
//                    startTime = "07:30",
//                    subjectCode = "LTH001",
//                    subjectName = "Lập trình hướng đối tượng"
//                ),CourseClass(
//                    classCode = "LTH001",
//                    dayOfWeek = 1,
//                    endPeriod = 1,
//                    endTime = "12:00",
//                    lecturerEmail = "william.henry.harrison@example-pet-store.vn",
//                    lecturerName = "Nguyễn Văn A",
//                    room = "A101",
//                    startPeriod = 1,
//                    startTime = "07:30",
//                    subjectCode = "LTH001",
//                    subjectName = "Lập trình hướng đối tượng"
//                ),CourseClass(
//                    classCode = "LTH001",
//                    dayOfWeek = 1,
//                    endPeriod = 1,
//                    endTime = "12:00",
//                    lecturerEmail = "william.henry.harrison@example-pet-store.vn",
//                    lecturerName = "Nguyễn Văn A",
//                    room = "A101",
//                    startPeriod = 1,
//                    startTime = "07:30",
//                    subjectCode = "LTH001",
//                    subjectName = "Lập trình hướng đối tượng"
//                )
//            )
//    }}
//}