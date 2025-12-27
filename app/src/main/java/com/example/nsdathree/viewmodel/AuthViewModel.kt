package com.example.nsdathree.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nsdathree.model.User
import com.example.nsdathree.repository.AuthRepository
import com.example.nsdathree.repository.UserRepository

class AuthViewModel(

) : ViewModel() {

    private val authRepo: AuthRepository = AuthRepository()
    private val userRepo: UserRepository = UserRepository()

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        authRepo.login(email, password) { ok, err ->
            if (ok) onSuccess() else onError(err!!)
        }
    }

    fun register(
        email: String,
        password: String,
        lat: Double,
        lng: Double,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        authRepo.register(email, password) { ok, err ->
            if (!ok) {
                onError(err ?: "Register failed")
                return@register
            }

            val firebaseUser = authRepo.currentUser()
            if (firebaseUser == null) {
                onError("Firebase user null")
                return@register
            }

            Log.d("Auth", "Register success: ${firebaseUser.uid}")

            userRepo.saveUser(
                User(
                    userId = firebaseUser.uid,
                    email = firebaseUser.email ?: "",
                    displayName = null,
                    latitude = lat,
                    longitude = lng
                )
            )

            onSuccess()
        }
    }

}
