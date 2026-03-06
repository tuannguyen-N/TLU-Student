package org.example.project.presentations.utils

object ValidationUtils {
    fun validateEmail(email: String): String? {
        if (email.isBlank()) return "Email không được để trống"
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
        if (!emailRegex.matches(email)) return "Email không hợp lệ"
        return null
    }

    fun validatePhone(phone: String): String? {
        if (phone.isBlank()) return "Số điện thoại không được để trống"
        if (!phone.matches(Regex("^[0-9]{9,11}$"))) return "Số điện thoại không hợp lệ"
        return null
    }

    fun validateRequired(value: String, fieldName: String): String? {
        if (value.isBlank()) return "$fieldName không được để trống"
        return null
    }
}