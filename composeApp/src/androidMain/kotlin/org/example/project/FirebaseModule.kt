package org.example.project
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseModule {

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
}