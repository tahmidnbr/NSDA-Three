package com.example.nsdathree.repository

import android.util.Log
import com.example.nsdathree.model.User
import com.google.firebase.firestore.FirebaseFirestore

class UserRepository {
    private val db = FirebaseFirestore.getInstance()

    fun saveUser(user: User) {
        db.collection("AppUsers")
            .document(user.userId)
            .set(user)
            .addOnSuccessListener {
                Log.d("Firestore", "User saved successfully")
            }
            .addOnFailureListener {
                Log.e("Firestore", "Save failed", it)
            }
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

    fun updateDisplayName(userId: String, newName: String) {
        db.collection("AppUsers")
            .document(userId)
            .update("displayName", newName)
            .addOnSuccessListener {
                Log.d("Firestore", "Display name updated")
            }
            .addOnFailureListener {
                Log.e("Firestore", "Failed to update name", it)
            }
    }


    fun getAllUsers(onResult: (List<User>) -> Unit) {
        db.collection("AppUsers")
            .get()
            .addOnSuccessListener {
                val users = it.toObjects(User::class.java)
                Log.d("Firestore", "Users fetched: ${users.size}")
                onResult(users)
            }
            .addOnFailureListener {
                Log.e("Firestore", "Fetch failed", it)
            }
    }

}