package com.example.nsdathree.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {

    private val auth = FirebaseAuth.getInstance()

    fun login(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun register(
        email: String,
        password: String,
        onResult: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun currentUser() = auth.currentUser

    fun logout() = auth.signOut()
}
