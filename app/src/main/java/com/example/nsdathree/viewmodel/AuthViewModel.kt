package com.example.nsdathree.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nsdathree.model.User
import com.example.nsdathree.repository.AuthRepository
import com.example.nsdathree.repository.UserRepository

class AuthViewModel(

) : ViewModel() {

    private val authRepo: AuthRepository = AuthRepository(),
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
                onError(err!!)
                return@register
            }

            val user = authRepo.currentUser() ?: return@register

            userRepo.saveUser(
                User(
                    userId = user.uid,
                    email = user.email ?: "",
                    latitude = lat,
                    longitude = lng
                )
            )

            onSuccess()
        }
    }
}
