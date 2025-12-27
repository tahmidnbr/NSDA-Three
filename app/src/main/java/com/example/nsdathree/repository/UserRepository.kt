package com.example.nsdathree.repository

import com.example.nsdathree.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    fun saveUser(user: User) {
        db.collection("AppUsers")
            .document(user.userId)
            .set(user)
    }

    fun updateLocation(uid: String, lat: Double, lng: Double) {
        db.collection("AppUsers")
            .document(uid)
            .update(
                mapOf(
                    "latitude" to lat,
                    "longitude" to lng
                )
            )
    }

    fun getAllUsers(onResult: (List<User>) -> Unit) {
        db.collection("AppUsers")
            .get()
            .addOnSuccessListener {
                onResult(it.toObjects(User::class.java))
            }
    }
}