package org.example.project.data.local

interface ImageBase64Storage {
    fun saveImageBase64(imageBase64: String?)
    fun getImageBase64(): String?
    fun clearImageBase64()
}