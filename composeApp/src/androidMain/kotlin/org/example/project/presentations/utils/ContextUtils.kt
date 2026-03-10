package org.example.project.presentations.utils

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

fun Context.openDialer(phone: String = "123123123") {
    val intent = Intent(Intent.ACTION_DIAL).apply {
        data = "tel:$phone".toUri()
    }
    startActivity(intent)
}