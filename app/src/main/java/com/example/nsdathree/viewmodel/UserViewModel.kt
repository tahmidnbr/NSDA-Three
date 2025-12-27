package com.example.nsdathree.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nsdathree.model.User
import com.example.nsdathree.repository.UserRepository

class UserViewModel() : ViewModel() {

    private val repository: UserRepository = UserRepository()
    var users: List<User> = emptyList()
        private set

    var loading: Boolean = false
        private set

    fun saveUser(user: User) {
        repository.saveUser(user)
    }

    fun updateLocation(
        userId: String,
        latitude: Double,
        longitude: Double
    ) {
        repository.updateLocation(userId, latitude, longitude)
    }

    fun loadAllUsers(onResult: (List<User>) -> Unit) {
        loading = true
        repository.getAllUsers { result ->
            users = result
            loading = false
            onResult(result)
        }
    }
}